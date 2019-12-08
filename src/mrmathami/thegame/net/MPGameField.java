package mrmathami.thegame.net;

import mrmathami.thegame.Config;
import mrmathami.thegame.GameEntities;
import mrmathami.thegame.GameField;
import mrmathami.thegame.GameStage;
import mrmathami.thegame.entity.*;
import mrmathami.thegame.entity.bullet.AbstractBullet;
import mrmathami.thegame.entity.enemy.AbstractEnemy;
import mrmathami.thegame.entity.tile.effect.ExplosionEffect;
import mrmathami.thegame.entity.tile.tower.*;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Multi-player game field.
 * Responsible for update opponent state to the player.
 * The field will play itself, important information such as player actions and heath will be fetch from remote host.
 */
public final class MPGameField extends GameField {
    private MPSocketController socket;

    public MPGameField(@Nonnull GameStage gameStage) {
        super(gameStage);
        this.socket = MPSocketController.getCurrentInstance();
    }

    /**
     * Overridden to doing nothing, we'll get player health from the opponent.
     * @param damage damage deal to player.
     */
    @Override
    public void harmPlayer(long damage) { }

    public final void tick() {
        this.tickCount += 1;

        // 1.1. Update UpdatableEntity
        for (final GameEntity entity : entities) {
            if (entity instanceof UpdatableEntity) ((UpdatableEntity) entity).onUpdate(this);
        }

        // 1.2. Update EffectEntity & LivingEntity
        for (final GameEntity entity : entities) {
            if (entity instanceof EffectEntity) {
                final EffectEntity effectEntity = (EffectEntity) entity;
                final Collection<LivingEntity> livingEntities = GameEntities.getAffectedEntities(entities,
                        effectEntity.getClass(), entity.getPosX(), entity.getPosY(), entity.getWidth(), entity.getHeight());
                for (final LivingEntity livingEntity : livingEntities) {
                    if (!effectEntity.onEffect(this, livingEntity)) break;
                }
            }
        }
        // 1.3. Update DestroyableEntity
        final List<GameEntity> destroyedEntities = new ArrayList<>(Config._TILE_MAP_COUNT);
        for (final GameEntity entity : entities) {
            if (entity instanceof DestroyableEntity && ((DestroyableEntity) entity).isDestroyed()) {
                if (entity instanceof DestroyListener) ((DestroyListener) entity).onDestroy(this);
                destroyedEntities.add(entity);
            }
        }

        // 2.1. Destroy entities
        for (GameEntity destroyEntity :
                destroyedEntities) {
            if (destroyEntity instanceof AbstractEnemy)
                sfxEntities.add(new ExplosionEffect(0, destroyEntity.getPosX() + Config.OFFSET/Config.TILE_SIZE, destroyEntity.getPosY() + Config.OFFSET/Config.TILE_SIZE));
        }
        entities.removeAll(destroyedEntities);

        final List<GameEntity> removeBulletEntities = new ArrayList<>(Config._TILE_MAP_COUNT);
        for (GameEntity entity :
                entities) {
            if (entity instanceof AbstractBullet)
            {
                if (!entity.isBeingOverlapped(0.0 - Config.OFFSET/Config.TILE_SIZE, 0.0 - Config.OFFSET/Config.TILE_SIZE, MPConfig.TILE_HORIZONTAL - entity.getWidth(), MPConfig.TILE_VERTICAL) && !entity.isBeingOverlapped(MPConfig.OPPONENT_START_X - Config.OFFSET/Config.TILE_SIZE, 0.0 - Config.OFFSET/Config.TILE_SIZE, MPConfig.TILE_VERTICAL - entity.getWidth(), MPConfig.TILE_HORIZONTAL))
                {
                    removeBulletEntities.add(entity);
                }
            }
        }
        entities.removeAll(removeBulletEntities);

        // 2.2. Destroy entities (removed becuz it deleting my entities :<)
//		entities.removeIf(entity -> !entity.isBeingOverlapped(0.0, 0.0, width, height));

        // 3. Spawn entities
        for (GameEntity entity : spawnEntities) {
            entities.add(entity);
            if (entity instanceof SpawnListener) ((SpawnListener) entity).onSpawn(this);
        }
        spawnEntities.clear();
        entities.addAll(sfxEntities);
        sfxEntities.clear();

        getAndProcessRemoteCommand();
    }

    /**
     * Handler for UPGRADE command from opponent.
     * @param x X position of tower on the field to upgrade tower.
     * @param y Y position of tower on the field to upgrade tower.
     */
    private void upgradeAtPosition(double x, double y) {
        for (GameEntity entity: entities) {
            if (entity instanceof AbstractTower) {
                if (Double.compare(entity.getPosX(), x + MPConfig.OPPONENT_START_X) == 0 &&
                        Double.compare(entity.getPosY(), y) == 0) {
                    ((AbstractTower) entity).doUpgrade();
                }
            }
        }
    }

    /**
     * Handler for SELL command from opponent.
     * @param x X position of tower on the field to sell tower.
     * @param y Y position of tower on the field to sell tower.
     */
    private void sellAtPosition(double x, double y) {
        for (GameEntity entity: entities) {
            if (entity instanceof AbstractTower) {
                if (Double.compare(entity.getPosX(), x + MPConfig.OPPONENT_START_X) == 0 &&
                        Double.compare(entity.getPosY(), y) == 0) {
                    ((AbstractTower) entity).doDestroy();
                }
            }
        }
    }

    /**
     * Main opponent-field command controller.
     * Responsible for getting remote command every tick and change the field accordingly.
     */
    private void getAndProcessRemoteCommand() {
        List<String> command = this.socket.getNextCommand();
        if (!command.isEmpty()) {
            if (command.get(0).equals("PLACE")) {
                switch (command.get(1)) {
                    case "1":
                        doSpawn(new NormalTower(0, MPConfig.OPPONENT_START_X + Integer.parseInt(command.get(2)), Integer.parseInt(command.get(3)), 90));
                        break;
                    case "2":
                        doSpawn(new MachineGunTower(0, MPConfig.OPPONENT_START_X + Integer.parseInt(command.get(2)), Integer.parseInt(command.get(3)), 90));
                        break;
                    case "3":
                        doSpawn(new RocketLauncherTower(0, MPConfig.OPPONENT_START_X + Integer.parseInt(command.get(2)), Integer.parseInt(command.get(3)), 90));
                        break;
                    case "4":
                        doSpawn(new RobotPoliceTower(0, MPConfig.OPPONENT_START_X + Integer.parseInt(command.get(2)), Integer.parseInt(command.get(3)), 90));
                        break;
                    default:
                        System.out.println("Unhandled tower code " + command.get(1));
                }
            } else if (command.get(0).equals("UPGRADE")) {
                upgradeAtPosition(Double.parseDouble(command.get(1)), Double.parseDouble(command.get(2)));
            } else if (command.get(0).equals("SELL")) {
                sellAtPosition(Double.parseDouble(command.get(1)), Double.parseDouble(command.get(2)));
            } else if (command.get(0).equals("STATE")) {
                setHealth(Long.parseLong(command.get(1)));
            }
        }
    }
}
