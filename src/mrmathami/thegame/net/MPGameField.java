package mrmathami.thegame.net;

import mrmathami.thegame.Config;
import mrmathami.thegame.GameEntities;
import mrmathami.thegame.GameField;
import mrmathami.thegame.GameStage;
import mrmathami.thegame.entity.*;
import mrmathami.thegame.entity.tile.tower.AbstractTower;
import mrmathami.thegame.entity.tile.tower.MachineGunTower;
import mrmathami.thegame.entity.tile.tower.NormalTower;
import mrmathami.thegame.entity.tile.tower.RocketLauncherTower;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class MPGameField extends GameField {
    MPSocketController socket;

    public MPGameField(@Nonnull GameStage gameStage) {
        super(gameStage);
        this.socket = MPSocketController.getCurrentInstance();
    }

    /**
     * Overridden to doing nothing, we'll get player health from the opponent.
     * @param damage
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
        entities.removeAll(destroyedEntities);

        // 2.2. Destroy entities (removed becuz it deleting my entities :<)
//		entities.removeIf(entity -> !entity.isBeingOverlapped(0.0, 0.0, width, height));

        // 3. Spawn entities
        for (GameEntity entity : spawnEntities) {
            entities.add(entity);
            if (entity instanceof SpawnListener) ((SpawnListener) entity).onSpawn(this);
        }
        spawnEntities.clear();

        getAndProcessRemoteCommand();
    }

    private void upgradeAtPosition(double x, double y) {
        System.out.println("Processing UPGRADE " + x + " " + y);
        for (GameEntity entity: entities) {
            if (entity instanceof AbstractTower) {
                if (Double.compare(entity.getPosX(), x + MPConfig.OPPONENT_START_X) == 0 &&
                        Double.compare(entity.getPosY(), y) == 0) {
                    ((AbstractTower) entity).upgrade();
                }
            }
        }
    }

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
                }
            } else if (command.get(0).equals("UPGRADE")) {
                upgradeAtPosition(Double.parseDouble(command.get(1)), Double.parseDouble(command.get(2)));
            } else if (command.get(0).equals("SELL")) {
                sellAtPosition(Double.parseDouble(command.get(1)), Double.parseDouble(command.get(2)));
            }
        }
    }
}
