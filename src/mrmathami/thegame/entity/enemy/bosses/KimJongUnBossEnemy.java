package mrmathami.thegame.entity.enemy.bosses;

import javafx.scene.media.AudioClip;
import mrmathami.thegame.Config;
import mrmathami.thegame.GameEntities;
import mrmathami.thegame.GameField;
import mrmathami.thegame.audio.GameAudio;
import mrmathami.thegame.entity.enemy.AbstractEnemy;
import mrmathami.thegame.entity.tile.cutineffect.BossCutInEffect;
import mrmathami.thegame.entity.tile.effect.ExplosionEffect;
import mrmathami.thegame.entity.tile.tower.AbstractTower;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Random;

public class KimJongUnBossEnemy extends BossEnemy {
    public KimJongUnBossEnemy (long createdTick, double posX, double posY) {
        super(createdTick, posX, posY, Config.KIM_JONG_UN_BOSS_ENEMY_WIDTH,
                Config.KIM_JONG_UN_BOSS_ENEMY_HEIGHT,
                Config.KIM_JONG_UN_BOSS_ENEMY_HEALTH,
                Config.KIM_JONG_UN_BOSS_ENEMY_ARMOR,
                Config.KIM_JONG_UN_BOSS_ENEMY_SPEED,
                Config.KIM_JONG_UN_BOSS_ENEMY_REWARD,
                Config.KIM_JONG_UN_BOSS_ENEMY_GID);
    }

    @Override
    public void skillCheck(@Nonnull GameField field) {
        if ((field.getTickCount() - getCreatedTick()) == Config.KIM_JONG_UN_BOSS_ENEMY_SKILL_ACTIVATE_TIME) {
            field.addSFX(new BossCutInEffect(field.getTickCount(), Config.KIM_JONG_UN_BOSS_ENEMY_CUT_IN_URI));
            GameAudio.getInstance().playSound(new AudioClip(GameAudio.kimJongUnSkillSound));
            int explosionBorder = Config.KIM_JONG_UN_BOSS_ENEMY_SKILL_RANGE;
            int explosionPosX = 0, explosionPosY = 0, best = -1;

            for (int tryCount = 1; tryCount <= 5; tryCount++) {
                int x = new Random().nextInt((int)Config.TILE_HORIZONTAL - 2 * explosionBorder) + explosionBorder;
                int y = new Random().nextInt((int)Config.TILE_VERTICAL - 2 * explosionBorder) + explosionBorder;
                Collection<AbstractTower> affectedTower = GameEntities.getFilteredOverlappedEntities(field.getEntities(),
                        AbstractTower.class, x - explosionBorder, y - explosionBorder, 2 * explosionBorder + 1, 2 * explosionBorder + 1);
                if (affectedTower.size() > best) {
                    best = affectedTower.size();
                    explosionPosX = x;
                    explosionPosY = y;
                }
            }
            for (int posX = explosionPosX - explosionBorder; posX <= explosionPosX + explosionBorder; posX++)
                for (int posY = explosionPosY - explosionBorder; posY <= explosionPosY + explosionBorder; posY++) {
                    field.addSFX(new ExplosionEffect(0, posX, posY));
                }
            for (AbstractTower towerEntity: GameEntities.getFilteredOverlappedEntities(field.getEntities(),
                    AbstractTower.class, explosionPosX - explosionBorder, explosionPosY - explosionBorder, 2 * explosionBorder + 1, 2 * explosionBorder + 1)) {
                towerEntity.doDestroy();
            }
            for (AbstractEnemy enemy: GameEntities.getFilteredOverlappedEntities(field.getEntities(),
                    AbstractEnemy.class, explosionPosX - explosionBorder, explosionPosY - explosionBorder, 2 * explosionBorder + 1, 2 * explosionBorder + 1)) {
                if (!(enemy instanceof BossEnemy)) enemy.doDestroy();
            }
        }
    }
}
