package jp.postclass.chordtraining.common;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.SoundPool;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.nio.CharBuffer;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import jp.postclass.chordtraining.Exception.ApplicationRuntimeException;

/**
 *
 */

public final class UtCommon {

    public static String decimalFormat(double n, String formatString) {
        DecimalFormat decimalFormat = new DecimalFormat(formatString);
        return decimalFormat.format(n);
    }

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
        return "chord/root/" + chordConstant.replace("__", "/");
    }

    public static boolean isTrueAny(boolean... booleans) {
        for (boolean b : booleans) {
            if (b) {
                return true;
            }
        }
        return false;
    }

    public static String getStringFromStream(InputStream is) throws IOException {
        try (InputStreamReader ir = new InputStreamReader(is, "UTF-8")) {
            StringBuilder sb = new StringBuilder();
            CharBuffer buff = CharBuffer.allocate(1024);
            while(ir.read(buff) != -1){
                buff.flip();
                sb.append(buff.toString());
                buff.clear();
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }
    }

    public static String getChordLabel (String chordConstant) {
        return getChordLabel(chordConstant, Constants.TENSION_NONE, Constants.TENSION_NONE, Constants.TENSION_NONE, Constants.TENSION_NONE);
    }

    public static String getChordLabel (String chordConstant, int t6, int t9, int t11, int t13) {
        String chordLabel = chordConstant.split("__")[1];
        chordLabel = chordLabel.replace("sh", "#");
        chordLabel = chordLabel.replace("_", "/");
        return chordLabel + getTensionChordLabel(t6, t9, t11, t13);
    }


    public static String getTensionChordLabel(int t6, int t9, int t11, int t13) {

        String result = "";

        switch (t6) {
            case Constants.TENSION_NATURAL:
                result += "/6";
                break;
            case Constants.TENSION_SH:
                result += "/+6";
                break;
            case Constants.TENSION_FL:
                result += "/-6";
                break;
        }

        switch (t9) {
            case Constants.TENSION_NATURAL:
                result += "/9";
                break;
            case Constants.TENSION_SH:
                result += "/+9";
                break;
            case Constants.TENSION_FL:
                result += "/-9";
                break;
        }

        switch (t11) {
            case Constants.TENSION_NATURAL:
                result += "/11";
                break;
            case Constants.TENSION_SH:
                result += "/+11";
                break;
            case Constants.TENSION_FL:
                result += "/-11";
                break;
        }

        switch (t13) {
            case Constants.TENSION_NATURAL:
                result += "/13";
                break;
            case Constants.TENSION_SH:
                result += "/+13";
                break;
            case Constants.TENSION_FL:
                result += "/-13";
                break;
        }

        return result;
    }

    public static String getTensionChordConstant(String chordConstant, int t6, int t9, int t11, int t13) {
        return chordConstant + getTensionChordName(t6, t9, t11, t13);
    }

    public static String getTensionChordName(int t6, int t9, int t11, int t13) {

        String result = "";

        switch (t6) {
            case Constants.TENSION_NATURAL:
                result += "_6";
                break;
            case Constants.TENSION_SH:
                result += "_sh6";
                break;
            case Constants.TENSION_FL:
                result += "_b6";
                break;
        }

        switch (t9) {
            case Constants.TENSION_NATURAL:
                result += "_9";
                break;
            case Constants.TENSION_SH:
                result += "_sh9";
                break;
            case Constants.TENSION_FL:
                result += "_b9";
                break;
        }

        switch (t11) {
            case Constants.TENSION_NATURAL:
                result += "_11";
                break;
            case Constants.TENSION_SH:
                result += "_sh11";
                break;
            case Constants.TENSION_FL:
                result += "_b11";
                break;
        }

        switch (t13) {
            case Constants.TENSION_NATURAL:
                result += "_13";
                break;
            case Constants.TENSION_SH:
                result += "_sh13";
                break;
            case Constants.TENSION_FL:
                result += "_b13";
                break;
        }

        return result;
    }
}
