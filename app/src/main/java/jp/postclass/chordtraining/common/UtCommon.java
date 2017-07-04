package jp.postclass.chordtraining.common;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.SoundPool;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import jp.postclass.chordtraining.Exception.ApplicationRuntimeException;

/**
 *
 */

public final class UtCommon {

    public static int loadSound(
            SoundPool soundPool,
            AssetManager assetManager,
            String assetsPath) {

        try (AssetFileDescriptor fd = assetManager.openFd(assetsPath)) {
            return soundPool.load(fd, 1);
        } catch (IOException e) {
            throw new ApplicationRuntimeException(e);
        }
    }

    public static String getCurrentDateString(String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(new Date());
    }

    public static int getRandomInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    public static String getChordPath (String chordConstant) {
        return "chord/root/" + chordConstant.replace('_', '/');
    }

    public static boolean isTrueAny(boolean... booleans) {
        for (boolean b : booleans) {
            if (b) {
                return true;
            }
        }
        return false;
    }

}
