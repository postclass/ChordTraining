package jp.postclass.chordtraining.generator;


import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

public class ChordAbcGenerator {

    private static final String newLine = "\r\n";
    private static final File baseDir = new File("D:/tmp/a");

    private Map<Integer, String> sMap = new HashMap<>();
    private Map<Integer, String> fMap = new HashMap<>();

    public static void main(String[] args) throws Exception{
        new ChordAbcGenerator().start();

    }

    private void start() throws Exception {
        getAbcMap();
        writeChordMj("A", 97, sMap);
        writeChordMn("A", 97, sMap);
        writeChordMj("Ab", 97, getFlatMap(sMap));
        writeChordMn("Ab", 97, getFlatMap(sMap));
        writeChordMj("As", 97, getSharpMap(sMap));
        writeChordMn("As", 97, getSharpMap(sMap));
        writeChordMj("B", 99, sMap);
        writeChordMn("B", 99, sMap);
        writeChordMj("Bb", 99, getFlatMap(sMap));
        writeChordMn("Bb", 99, getFlatMap(sMap));
        writeChordMj("Bs", 99, getSharpMap(sMap));
        writeChordMn("Bs", 99, getSharpMap(sMap));
        writeChordMj("C", 100, sMap);
        writeChordMn("C", 100, sMap);
        writeChordMj("Cb", 100, getFlatMap(sMap));
        writeChordMn("Cb", 100, getFlatMap(sMap));
        writeChordMj("Cs", 100, getSharpMap(sMap));
        writeChordMn("Cs", 100, getSharpMap(sMap));
        writeChordMj("D", 102, sMap);
        writeChordMn("D", 102, sMap);
        writeChordMj("Db", 102, getFlatMap(sMap));
        writeChordMn("Db", 102, getFlatMap(sMap));
        writeChordMj("Ds", 102, getSharpMap(sMap));
        writeChordMn("Ds", 102, getSharpMap(sMap));
        writeChordMj("E", 104, sMap);
        writeChordMn("E", 104, sMap);
        writeChordMj("Eb", 104, getFlatMap(sMap));
        writeChordMn("Eb", 104, getFlatMap(sMap));
        writeChordMj("Es", 104, getSharpMap(sMap));
        writeChordMn("Es", 104, getSharpMap(sMap));
        writeChordMj("F", 105, sMap);
        writeChordMn("F", 105, sMap);
        writeChordMj("Fb", 105, getFlatMap(sMap));
        writeChordMn("Fb", 105, getFlatMap(sMap));
        writeChordMj("Fs", 105, getSharpMap(sMap));
        writeChordMn("Fs", 105, getSharpMap(sMap));
        writeChordMj("G", 107, sMap);
        writeChordMn("G", 107, sMap);
        writeChordMj("Gb", 107, getFlatMap(sMap));
        writeChordMn("Gb", 107, getFlatMap(sMap));
        writeChordMj("Gs", 107, getSharpMap(sMap));
        writeChordMn("Gs", 107, getSharpMap(sMap));
    }

    public void writeChordMj(String rootName, int rootNo, Map<Integer, String> baseMap) throws Exception {

        File dir = new File(baseDir, rootName);
        dir.mkdir();

        try (FileOutputStream outputStream = new FileOutputStream(new File(dir, rootName + ".abc"))) {
            try (OutputStreamWriter writer = new OutputStreamWriter(outputStream, "utf-8")) {
                String fileString = getHeader();
                fileString += "[";
                fileString += baseMap.get(rootNo);
                fileString += baseMap.get(rootNo + 4);
                fileString += baseMap.get(rootNo + 7);
                fileString += "]4 |";

                writer.write(fileString);
                writer.flush();
            }
        }

        // sus4
        try (FileOutputStream outputStream = new FileOutputStream(new File(dir, rootName + "sus4.abc"))) {
            try (OutputStreamWriter writer = new OutputStreamWriter(outputStream, "utf-8")) {
                String fileString = getHeader();
                fileString += "[";
                fileString += baseMap.get(rootNo);
                fileString += baseMap.get(rootNo + 5);
                fileString += baseMap.get(rootNo + 7);
                fileString += "]4 |";

                writer.write(fileString);
                writer.flush();
            }
        }

        // -5
        try (FileOutputStream outputStream = new FileOutputStream(new File(dir, rootName + "f5.abc"))) {
            try (OutputStreamWriter writer = new OutputStreamWriter(outputStream, "utf-8")) {
                String fileString = getHeader();
                fileString += "[";
                fileString += baseMap.get(rootNo);
                fileString += baseMap.get(rootNo + 4);
                fileString += getFlat(baseMap, rootNo + 7);
                fileString += "]4 |";

                writer.write(fileString);
                writer.flush();
            }
        }

        // aug
        try (FileOutputStream outputStream = new FileOutputStream(new File(dir, rootName + "aug.abc"))) {
            try (OutputStreamWriter writer = new OutputStreamWriter(outputStream, "utf-8")) {
                String fileString = getHeader();
                fileString += "[";
                fileString += baseMap.get(rootNo);
                fileString += baseMap.get(rootNo + 4);
                fileString += getSharp(baseMap, rootNo + 7);
                fileString += "]4 |";

                writer.write(fileString);
                writer.flush();
            }
        }

        // 6
        try (FileOutputStream outputStream = new FileOutputStream(new File(dir, rootName + "6.abc"))) {
            try (OutputStreamWriter writer = new OutputStreamWriter(outputStream, "utf-8")) {
                String fileString = getHeader();
                fileString += "[";
                fileString += baseMap.get(rootNo);
                fileString += baseMap.get(rootNo + 4);
                fileString += baseMap.get(rootNo + 7);
                fileString += baseMap.get(rootNo + 9);
                fileString += "]4 |";

                writer.write(fileString);
                writer.flush();
            }
        }

        // 7
        try (FileOutputStream outputStream = new FileOutputStream(new File(dir, rootName + "7.abc"))) {
            try (OutputStreamWriter writer = new OutputStreamWriter(outputStream, "utf-8")) {
                String fileString = getHeader();
                fileString += "[";
                fileString += baseMap.get(rootNo);
                fileString += baseMap.get(rootNo + 4);
                fileString += baseMap.get(rootNo + 7);
                fileString += getFlat(baseMap, rootNo + 11);
                fileString += "]4 |";

                writer.write(fileString);
                writer.flush();
            }
        }

        // M7
        try (FileOutputStream outputStream = new FileOutputStream(new File(dir, rootName + "M7.abc"))) {
            try (OutputStreamWriter writer = new OutputStreamWriter(outputStream, "utf-8")) {
                String fileString = getHeader();
                fileString += "[";
                fileString += baseMap.get(rootNo);
                fileString += baseMap.get(rootNo + 4);
                fileString += baseMap.get(rootNo + 7);
                fileString += baseMap.get(rootNo + 11);
                fileString += "]4 |";

                writer.write(fileString);
                writer.flush();
            }
        }

        // add9
        try (FileOutputStream outputStream = new FileOutputStream(new File(dir, rootName + "add9.abc"))) {
            try (OutputStreamWriter writer = new OutputStreamWriter(outputStream, "utf-8")) {
                String fileString = getHeader();
                fileString += "[";
                fileString += baseMap.get(rootNo);
                fileString += baseMap.get(rootNo + 4);
                fileString += baseMap.get(rootNo + 7);
                fileString += baseMap.get(rootNo + 14);
                fileString += "]4 |";

                writer.write(fileString);
                writer.flush();
            }
        }

        // 11
        try (FileOutputStream outputStream = new FileOutputStream(new File(dir, rootName + "11.abc"))) {
            try (OutputStreamWriter writer = new OutputStreamWriter(outputStream, "utf-8")) {
                String fileString = getHeader();
                fileString += "[";
                fileString += baseMap.get(rootNo);
                fileString += baseMap.get(rootNo + 4);
                fileString += baseMap.get(rootNo + 7);
                fileString += baseMap.get(rootNo + 17);
                fileString += "]4 |";

                writer.write(fileString);
                writer.flush();
            }
        }
    }

    public void writeChordMn(String rootName, int rootNo, Map<Integer, String> baseMap) throws Exception {

        File dir = new File(baseDir, rootName + "m");
        dir.mkdir();

        try (FileOutputStream outputStream = new FileOutputStream(new File(dir, rootName + "m.abc"))) {
            try (OutputStreamWriter writer = new OutputStreamWriter(outputStream, "utf-8")) {
                String fileString = getHeader();
                fileString += "[";
                fileString += baseMap.get(rootNo);
                fileString += getFlat(baseMap, rootNo + 4);
                fileString += baseMap.get(rootNo + 7);
                fileString += "]4 |";

                writer.write(fileString);
                writer.flush();
            }
        }

        // -5
        try (FileOutputStream outputStream = new FileOutputStream(new File(dir, rootName + "dim.abc"))) {
            try (OutputStreamWriter writer = new OutputStreamWriter(outputStream, "utf-8")) {
                String fileString = getHeader();
                fileString += "[";
                fileString += baseMap.get(rootNo);
                fileString += getFlat(baseMap, rootNo + 4);
                fileString += getFlat(baseMap, rootNo + 7);
                fileString += "]4 |";

                writer.write(fileString);
                writer.flush();
            }
        }

        // +5
        try (FileOutputStream outputStream = new FileOutputStream(new File(dir, rootName + "ms5.abc"))) {
            try (OutputStreamWriter writer = new OutputStreamWriter(outputStream, "utf-8")) {
                String fileString = getHeader();
                fileString += "[";
                fileString += baseMap.get(rootNo);
                fileString += getFlat(baseMap, rootNo + 4);
                fileString += getSharp(baseMap, rootNo + 7);
                fileString += "]4 |";

                writer.write(fileString);
                writer.flush();
            }
        }

        // 6
        try (FileOutputStream outputStream = new FileOutputStream(new File(dir, rootName + "m6.abc"))) {
            try (OutputStreamWriter writer = new OutputStreamWriter(outputStream, "utf-8")) {
                String fileString = getHeader();
                fileString += "[";
                fileString += baseMap.get(rootNo);
                fileString += getFlat(baseMap, rootNo + 4);
                fileString += baseMap.get(rootNo + 7);
                fileString += baseMap.get(rootNo + 9);
                fileString += "]4 |";

                writer.write(fileString);
                writer.flush();
            }
        }

        // 7
        try (FileOutputStream outputStream = new FileOutputStream(new File(dir, rootName + "m7.abc"))) {
            try (OutputStreamWriter writer = new OutputStreamWriter(outputStream, "utf-8")) {
                String fileString = getHeader();
                fileString += "[";
                fileString += baseMap.get(rootNo);
                fileString += getFlat(baseMap, rootNo + 4);
                fileString += baseMap.get(rootNo + 7);
                fileString += getFlat(baseMap, rootNo + 11);
                fileString += "]4 |";

                writer.write(fileString);
                writer.flush();
            }
        }

        // 7-5
        try (FileOutputStream outputStream = new FileOutputStream(new File(dir, rootName + "m7f5.abc"))) {
            try (OutputStreamWriter writer = new OutputStreamWriter(outputStream, "utf-8")) {
                String fileString = getHeader();
                fileString += "[";
                fileString += baseMap.get(rootNo);
                fileString += getFlat(baseMap, rootNo + 4);
                fileString += getFlat(baseMap, rootNo + 7);
                fileString += getFlat(baseMap, rootNo + 11);
                fileString += "]4 |";

                writer.write(fileString);
                writer.flush();
            }
        }

        // M7
        try (FileOutputStream outputStream = new FileOutputStream(new File(dir, rootName + "mM7.abc"))) {
            try (OutputStreamWriter writer = new OutputStreamWriter(outputStream, "utf-8")) {
                String fileString = getHeader();
                fileString += "[";
                fileString += baseMap.get(rootNo);
                fileString += getFlat(baseMap, rootNo + 4);
                fileString += baseMap.get(rootNo + 7);
                fileString += baseMap.get(rootNo + 11);
                fileString += "]4 |";

                writer.write(fileString);
                writer.flush();
            }
        }

        // add9
        try (FileOutputStream outputStream = new FileOutputStream(new File(dir, rootName + "madd9.abc"))) {
            try (OutputStreamWriter writer = new OutputStreamWriter(outputStream, "utf-8")) {
                String fileString = getHeader();
                fileString += "[";
                fileString += baseMap.get(rootNo);
                fileString += getFlat(baseMap, rootNo + 4);
                fileString += baseMap.get(rootNo + 7);
                fileString += baseMap.get(rootNo + 14);
                fileString += "]4 |";

                writer.write(fileString);
                writer.flush();
            }
        }

        // 11
        try (FileOutputStream outputStream = new FileOutputStream(new File(dir, rootName + "m11.abc"))) {
            try (OutputStreamWriter writer = new OutputStreamWriter(outputStream, "utf-8")) {
                String fileString = getHeader();
                fileString += "[";
                fileString += baseMap.get(rootNo);
                fileString += getFlat(baseMap, rootNo + 4);
                fileString += baseMap.get(rootNo + 7);
                fileString += baseMap.get(rootNo + 17);
                fileString += "]4 |";

                writer.write(fileString);
                writer.flush();
            }
        }
    }

    private Map<Integer, String> getFlatMap(Map<Integer, String> baseMap) {
        Map<Integer, String> result = new HashMap<>();

        for (Integer key : baseMap.keySet()) {
            result.put(key, getFlat(baseMap, key));
        }

        return result;
    }

    private Map<Integer, String> getSharpMap(Map<Integer, String> baseMap) {
        Map<Integer, String> result = new HashMap<>();

        for (Integer key : baseMap.keySet()) {
            result.put(key, getSharp(baseMap, key));
        }

        return result;
    }

    private String getFlat(Map<Integer, String> baseMap, int no) {
        String result = "_" + baseMap.get(no);
        result = result.replace("_^", "");
        result = result.replace("^_", "");
        return result;
    }

    private String getSharp(Map<Integer, String> baseMap, int no) {
        String result = "^" + baseMap.get(no);
        result = result.replace("_^", "");
        result = result.replace("^_", "");
        return result;
    }

    private String getHeader() {
        return "I:abc-charset utf-8" + newLine
                + "X:1" + newLine
                + "T:" + newLine
                + "M:4/4" + newLine
                + "L:1/8" + newLine
                + "K:C treble" + newLine
                + "%" + newLine;
    }

    private void getAbcMap() {
        int no = 88;

        sMap.put(no, "C,");
        fMap.put(no, "C,");

        no++;
        sMap.put(no, "^C,");
        fMap.put(no, "_C,");

        no++;
        sMap.put(no, "D,");
        fMap.put(no, "D,");

        no++;
        sMap.put(no, "^D,");
        fMap.put(no, "_E,");

        no++;
        sMap.put(no, "E,");
        fMap.put(no, "E,");

        no++;
        sMap.put(no, "F,");
        fMap.put(no, "F,");

        no++;
        sMap.put(no, "^F,");
        fMap.put(no, "_G,");

        no++;
        sMap.put(no, "G,");
        fMap.put(no, "G,");

        no++;
        sMap.put(no, "^G,");
        fMap.put(no, "_A,");

        no++;
        sMap.put(no, "A,");
        fMap.put(no, "A,");

        no++;
        sMap.put(no, "^A,");
        fMap.put(no, "_B,");

        no++;
        sMap.put(no, "B,");
        fMap.put(no, "B,");


        no++; // 100
        sMap.put(no, "C");
        fMap.put(no, "C");

        no++;
        sMap.put(no, "^C");
        fMap.put(no, "_C");

        no++;
        sMap.put(no, "D");
        fMap.put(no, "D");

        no++;
        sMap.put(no, "^D");
        fMap.put(no, "_E");

        no++;
        sMap.put(no, "E");
        fMap.put(no, "E");

        no++;
        sMap.put(no, "F");
        fMap.put(no, "F");

        no++;
        sMap.put(no, "^F");
        fMap.put(no, "_G");

        no++;
        sMap.put(no, "G");
        fMap.put(no, "G");

        no++;
        sMap.put(no, "^G");
        fMap.put(no, "_A");

        no++;
        sMap.put(no, "A");
        fMap.put(no, "A");

        no++;
        sMap.put(no, "^A");
        fMap.put(no, "_B");

        no++;
        sMap.put(no, "B");
        fMap.put(no, "B");


        no++; // 124
        sMap.put(no, "c");
        fMap.put(no, "c");

        no++;
        sMap.put(no, "^c");
        fMap.put(no, "_c");

        no++;
        sMap.put(no, "d");
        fMap.put(no, "d");

        no++;
        sMap.put(no, "^d");
        fMap.put(no, "_e");

        no++;
        sMap.put(no, "e");
        fMap.put(no, "e");

        no++;
        sMap.put(no, "f");
        fMap.put(no, "f");

        no++;
        sMap.put(no, "^f");
        fMap.put(no, "_g");

        no++;
        sMap.put(no, "g");
        fMap.put(no, "g");

        no++;
        sMap.put(no, "^g");
        fMap.put(no, "_a");

        no++;
        sMap.put(no, "a");
        fMap.put(no, "a");

        no++;
        sMap.put(no, "^a");
        fMap.put(no, "_b");

        no++;
        sMap.put(no, "b");
        fMap.put(no, "b");


        no++; // 148
        sMap.put(no, "c'");
        fMap.put(no, "c'");

        no++;
        sMap.put(no, "^c'");
        fMap.put(no, "_c'");

        no++;
        sMap.put(no, "d'");
        fMap.put(no, "d'");

        no++;
        sMap.put(no, "^d'");
        fMap.put(no, "_e'");

        no++;
        sMap.put(no, "e'");
        fMap.put(no, "e'");

        no++;
        sMap.put(no, "f'");
        fMap.put(no, "f'");

        no++;
        sMap.put(no, "^f'");
        fMap.put(no, "_g'");

        no++;
        sMap.put(no, "g'");
        fMap.put(no, "g'");

        no++;
        sMap.put(no, "^g'");
        fMap.put(no, "_a'");

        no++;
        sMap.put(no, "a'");
        fMap.put(no, "a'");

        no++;
        sMap.put(no, "^a'");
        fMap.put(no, "_b'");

        no++;
        sMap.put(no, "b'");
        fMap.put(no, "b'");
    }


}
