import apple.laf.JRSUIUtils;

import java.util.*;

/**
 * Created by stepa on 17.03.15.
 */

public class CipherEncoding {
    private static int keyLength;

    public static String decode(String str, String key) {
        StringBuilder cipherStr = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            cipherStr.append(String.valueOf ( ALPHABET.charAt ( (ALPHABET.indexOf(str.charAt(i)) + ALPHABET.indexOf(key.charAt(i%key.length()))) %26) ) );
        }
        return cipherStr.toString();
    }

    public static String encode(String str, String key) {
        int index;
        StringBuilder encodedStr = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if ((index = (ALPHABET.indexOf(str.charAt(i)) - ALPHABET.indexOf(key.charAt(i%key.length()))) %26) < 0)
                index = 26 + index;
            encodedStr.append(String.valueOf ( ALPHABET.charAt ( index ) ));
        }
        return encodedStr.toString();
    }

    public static void addToMap(HashMap<String, Integer> map, String key) {
        if (map.containsKey(key)) {
            int meetTimes = map.get(key);
            map.put(key, meetTimes + 1);
        } else {
            map.put(key, 1);
        }
    }

    public static void createArr(ArrayList<String> arr, TreeMap<String, Integer> smap, int counter) {
        for (Map.Entry<String, Integer> entry : smap.entrySet()) {
            if (entry.getValue() >= 2 && counter > 0) {
                arr.add(entry.getKey());
                counter--;
            } else {
                break;
            }
        }
    }

    public static String statisticsProcessing(ArrayList<String> arr) {
        String temp;
        int index;
        HashMap<String, Integer> result = new HashMap<>();
        for (int i = 0; i < arr.size(); i++ ) {
            for (int j = 0; j < MOST_FREQUENT_LETTERS.length(); j++) {
                if ((index = (ALPHABET.indexOf(arr.get(i)) - ALPHABET.indexOf(MOST_FREQUENT_LETTERS.charAt(j)))%26) < 0) {
                    index = 26 + index;
                }
                temp = String.valueOf(ALPHABET.charAt( index ));
                addToMap(result, temp);
            }
        }
        ReverseComparator comp = new ReverseComparator(result);
        TreeMap<String, Integer> sortedmap = new TreeMap<>(comp);
        sortedmap.putAll(result);
        return sortedmap.firstKey();
    }

    public static int evklid(int a, int b) {
        while (b!=0 && b!=1 && b!=5) {
            int tmp = a%b;
            a = b;
            b = tmp;
        }
        return a;
    }

    public static int findNOD(List<String> bigrams) {
        ArrayList<Integer> distance = new ArrayList<>();
        int nod;
        //Looking only for 3 most frequently appeared bigrams
        for (String str : bigrams) {
            int startWith, endWith;
            startWith = CIPHER.indexOf(str, 0);
            while (CIPHER.indexOf(str, startWith + 1) != -1) {
                endWith = CIPHER.indexOf(str, startWith + 1);
                if (endWith - startWith > 1)
                    distance.add(endWith - startWith);
                startWith = endWith;
            }
        }
        nod = distance.get(0);
        for (int i = 1; i < distance.size(); i++) {
            nod = evklid(nod,distance.get(i));
        }
        System.out.print(nod);
        nod = 6; //***********
        return nod;
    }

    public static void main(String[] arg) {
        //String cipher = "DYDUXRMHTVDVNQDQNWDYDIXRMHARTOOJGWNQD";
        String tempStr;

        HashMap<String, Integer> bigramms = new HashMap<String, Integer>();
        ReverseComparator comp = new ReverseComparator(bigramms);
        TreeMap<String, Integer> sortedmap = new TreeMap<>(comp);
        ArrayList<String> arrBigrams = new ArrayList<>();

        for (int i = 0; i < CIPHER.length() - 1; i++) {
            tempStr = CIPHER.substring(i, i+2);
            addToMap(bigramms, tempStr);
        }

        sortedmap.putAll(bigramms);
        createArr(arrBigrams, sortedmap, 4);

        for (String str :arrBigrams) {
            System.out.println(str);
        }

        keyLength = findNOD(arrBigrams);
        ArrayList<String> resultArr = new ArrayList<>();
        HashMap<String, Integer> letters = new HashMap<>();
        ReverseComparator compTwo = new ReverseComparator(letters);
        TreeMap<String, Integer> sortedmapTwo = new TreeMap<>(compTwo);
        ArrayList<String> arrLetters = new ArrayList<>();

        for (int i = 0; i < keyLength; i++) {
            sortedmapTwo.clear();
            letters.clear();
            arrLetters.clear();
            for (int j = i; j < CIPHER.length(); j = j + keyLength) {
                tempStr = String.valueOf(CIPHER.charAt(j));
                addToMap(letters, tempStr);
            }
            sortedmapTwo.putAll(letters);

            createArr(arrLetters, sortedmapTwo, 3);
            String ourLet = statisticsProcessing(arrLetters);
            resultArr.add(ourLet);
        }

        String decoded = decode("sampletextthisita", "key");
        System.out.println("Decoded: " + decoded);
        String origin = encode(decoded, "key");
        System.out.println("Encoded: " + origin);

    }

    private static final String CIPHER = "xtxafslwpmccpjifvbdbsmccpjifvjpgxqycccxavpecxmpegwiyjepjiwflthlxzphdmmvzjhmgkftcrlvrrcqxjmhveecgiowmvyitmkjriviovpnkskjrdtjhirjbildgvvxtebdhlxiqifebeqdtahvuwwgaemlgixdudsghdnpfiwngivphjqdtxavclwpeemigixdqd";
    //private static final String CIPHER = "vptnvffuntshtarptymjwzirappljmhhqvsubwlzzygvtyitarptyiougxiuydtgzhhvvmumshwkzgstfmekvmpkswdgbilvjljmglmjfqwioiivknulvvfemioiemojtywdsajtwmtcgluysdsumfbieugmvalvxkjduetukatymvkqzhvqvgvptytjwwldyeevquhlulwpkt";
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    private static final String MOST_FREQUENT_LETTERS = "eaot";
}
