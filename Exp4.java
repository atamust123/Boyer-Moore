
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

public class Exp4 {

    public static int max(int a, int b) {
        return (a > b) ? a : b;
    }

    public static int min(int a, int b) {
        return (a < b) ? a : b;
    }

    public static int[] badCharHeuristic(char[] str, int size) {
        int i;
        int badChar[] = new int[256];
        for (i = 0; i < 256; i++) {
            badChar[i] = -1;
        }

        for (i = 0; i < size; i++) {
            badChar[(int) str[i]] = i;
        }
        return badChar;
    }

    public static boolean BoyerMoore(char txt[], char pattern[], int[] i, int startIndex) {
        int m = ArrayLen(pattern);
        int n = ArrayLen(txt);
        
        
        int badChar[] = badCharHeuristic(pattern, m);

        int index = startIndex; //start here
        while (index <= (n - m)) {
            int j = m - 1;

            while (j >= 0 && pattern[j] == txt[index + j]) {
                j--;
            }

            if (j < 0) {
                i[0] = index;
                index += (index + m < n) ? m - badChar[txt[index + m]] : 1;

                return true;
            } else {

                try {
                    index += max(1, j - badChar[txt[index + j]]);
                } catch (Exception e) {
                    index += 1;
                    break;
                }
            }
        }
        return false;
    }

    public static double parseInt(char[] chars) {//parse int with boyerMoore
        // control is there any million
        double reverse = 0;
        int coeff = 1;
        boolean doubleVarExist = false;
        for (int i = 0; i < ArrayLen(chars); i++) {
            if (chars[i] < 58 && chars[i] > 47) {//if it is number
                if (doubleVarExist) {
                    coeff *= 10;
                } else {
                    reverse *= 10;
                }
                reverse
                        += (double) (Character.getNumericValue(chars[i])) / coeff;
            } else if (chars[i] == 44) {//virg
            } else if (chars[i] == 46) { //dot
                doubleVarExist = true;
            } else if (reverse > 0) {
                break;
            }
        }
        int[] i = {-1};
        if (BoyerMoore(chars, "million".toCharArray(), i, 0)) {
            reverse *= 1000000;
        }
        return reverse;
    }

    public static double read(File f2, char[] pattern, char[] secondPattern)
            throws FileNotFoundException, IOException {
        //read with boyer moore given txt
        BufferedReader bf = new BufferedReader(new FileReader(f2));
        char[] s = bf.readLine().toCharArray();
        while (s != null) {
            int index[] = {-1};
            boolean x = BoyerMoore(s, pattern, index, 0);
            if (x) { //for example if you see Area
                if (ArrayLen(secondPattern) != 0) {//if second pattern is given search secondPattern
                    s = bf.readLine().toCharArray();
                    x = BoyerMoore(s, secondPattern, index, 0);
                    while (s != null) {
                        if (x) {
                            break;
                        } else {
                            s = bf.readLine().toCharArray();
                            x = BoyerMoore(s, secondPattern, index, 0);

                        }
                    }

                }
                s = bf.readLine().toCharArray();
                x = BoyerMoore(s, "text".toCharArray(), index, 0);
                if (x) {
                    return parseInt(s);
                } else {
                    s = bf.readLine().toCharArray();
                }
            } else {
                if (bf.ready()) {
                    s = bf.readLine().toCharArray();
                } else {
                    return 0;
                }
            }
        }
        return 0;
    }

    public static char[] CountryName(File f2, char[] pattern)
            throws FileNotFoundException, IOException {//Search countryName with boyerMoore
        BufferedReader bf = new BufferedReader(new FileReader(f2));
        char[] s = bf.readLine().toCharArray();
        while (s != null) {
            int index[] = {-1};
            boolean x = BoyerMoore(s, pattern, index, 0);
            if (x) {
                s = bf.readLine().toCharArray();
                x = BoyerMoore(s, "text".toCharArray(), index, 0);
                if (x) {
                    char name[] = new char[ArrayLen(s) - index[0] - 4];
                    for (int i = index[0] + 8, j = 0; i < ArrayLen(s) - 1; i++, j++) {
                        name[j] = s[i];
                    }
                    return name;
                } else {
                    s = bf.readLine().toCharArray();
                }
            } else {
                s = bf.readLine().toCharArray();
            }
        }

        return pattern;
    }

    public static void main(String[] args) throws IOException {
        System.setOut(new PrintStream(new FileOutputStream(args[2])));     
        BST populationBST = new BST();
        BST area_totalBST = new BST();
        BST area_landBST = new BST();
        BST area_waterBST = new BST();
        BST median_age_maleBST = new BST();
        BST median_age_femaleBST = new BST();
        BST birth_rateBST = new BST();//create bst to sort
        BST death_rateBST = new BST();
        BST literacy_femaleBST = new BST();
        BST airportsBST = new BST();
        File[] files = new File(args[0]).listFiles();
        int size = 0;
        for (File f : files) {
            File subFiles[] = f.listFiles();
            if (subFiles != null) {
                size += FileLen(subFiles);
                for (File f2 : subFiles) {
                    int pop = (int) read(f2, "Population\"".toCharArray(),
                            "".toCharArray());
                    double birth = read(f2, "Birth".toCharArray(),
                            "".toCharArray());
                    double death = read(f2, "Death".toCharArray(),
                            "".toCharArray());
                    int airports = (int) read(f2, "Airports\"".toCharArray(),
                            "".toCharArray());
                    char[] name = CountryName(f2,
                            "conventional short form".toCharArray());

                    double totalArea = read(f2, "Area\"".toCharArray(),
                            "total\"".toCharArray());

                    double landArea = read(f2, "Area\"".toCharArray(),
                            "land\"".toCharArray());

                    double waterArea = read(f2, "Area\"".toCharArray(),
                            "water\"".toCharArray());

                    double literacyFemale = read(f2, "Literacy\"".toCharArray(),
                            "female\":".toCharArray());

                    double medianAgeMale = read(f2, "Median age".toCharArray(),
                            "\"male\":".toCharArray());

                    double medianAgeFemale = read(f2, "Median age".toCharArray(),
                            "\"female\":".toCharArray());
                    populationBST.insert(pop, name);
                    area_totalBST.insert(totalArea, name);
                    area_landBST.insert(landArea, name);
                    area_waterBST.insert(waterArea, name);
                    median_age_maleBST.insert(medianAgeMale, name);
                    median_age_femaleBST.insert(medianAgeFemale, name);
                    birth_rateBST.insert(birth, name);
                    death_rateBST.insert(death, name);
                    literacy_femaleBST.insert(literacyFemale, name);
                    airportsBST.insert(airports, name);
                }
            }
        }
        File input = new File(args[1]);
        BufferedReader bf = new BufferedReader(new FileReader(input));
        char[] s;
        int i[] = {-1};
        while (bf.ready()) {
            s = bf.readLine().toCharArray();
            char[][][] a = new char[10][][];
            a[0] = split(s, area_totalBST.inOrder(size),
                    size, "area-total".toCharArray());
            a[1] = split(s, area_landBST.inOrder(size),
                    size, "area-land".toCharArray());
            a[2] = split(s, area_waterBST.inOrder(size),
                    size, "area-water".toCharArray());
            a[3] = split(s, populationBST.inOrder(size),
                    size, "population".toCharArray());
            a[4] = split(s, median_age_maleBST.inOrder(size),
                    size, "median_age-male".toCharArray());
            a[5] = split(s, median_age_femaleBST.inOrder(size),
                    size, "median_age-female".toCharArray());
            a[6] = split(s, birth_rateBST.inOrder(size),
                    size, "birth_rate".toCharArray());
            a[7] = split(s, death_rateBST.inOrder(size),
                    size, "death_rate".toCharArray());
            a[8] = split(s, literacy_femaleBST.inOrder(size),
                    size, "literacy-female".toCharArray());
            a[9] = split(s, airportsBST.inOrder(size),
                    size, "airports".toCharArray());
            int newSize = 0;
            for (int counter = 0; counter < 10; counter++) {
                if (a[counter] != null) {
                    newSize += ArrayLen(a[counter]);
                }
            }
            char[][] newMustSort = new char[newSize][];
            int j = 0;
            for (int counter = 0; counter < 10; counter++) {
                if (a[counter] != null) {
                    for (char[] item : a[counter]) {
                        newMustSort[j++] = item;
                    }

                }
            }
            Sort(newMustSort);
        }

    }

    public static char[][] split(char s[], char[][] countries, int size, char[] pattern) {
        int i[] = {-1};//split input file
        if (BoyerMoore(s, pattern, i, 0)) {
            int indexTop = ArrayLen(s);
            int indexLast = ArrayLen(s);
            int index = i[0];
            if (BoyerMoore(s, "top".toCharArray(), i, index)) {
                indexTop = i[0];
            }
            if (BoyerMoore(s, "last".toCharArray(), i, index)) {
                indexLast = i[0];
            }

            int times = 0, j;
            if (indexLast > indexTop) {
                j = indexTop + 4;
            } else {
                j = indexLast + 5;
            }
            for (; j < ArrayLen(s); j++) {
                if (s[j] != '+') {
                    times *= 10;
                    times += (s[j] - 48);
                } else {
                    break;
                }
            }
            if (indexTop > indexLast) {
                return print(countries, size, times, 'l');
            } else {
                return print(countries, size, times, 't');
            }
        }
        return null;
    }

    public static char[][] print(char[][] countries, int size, int times, char c) {
        char mustSort[][] = new char[times][];//read input files
        if (c == 't') {//top or last
            for (int j = size - 1; j >= 0; j--) {
                if (times > 0) {
                    mustSort[times - 1] = countries[j];
                }
                times--;
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (times > 0) {
                    mustSort[times - 1] = countries[i];
                }
                times--;

            }
        }
        Sort(mustSort, 0, ArrayLen(mustSort) - 1, 0);
        return mustSort;
    }

    public static void Sort(char[][] array) {
        Sort(array, 0, ArrayLen(array) - 1, 0);
        array = findDuplicates(array);
        System.out.print("[");
        for (int i = 0; i < array.length; i++) {
            for (int t=0;t<ArrayLen(array[i])-5;t++) {
                System.out.print(array[i][t]);
            }
            if (i == array.length - 1) {
                System.out.println("]");
            } else {
                System.out.print(", ");
            }
        }
    }

    public static void Sort(char[][] array, int lo, int hi, int d) {
        if (hi <= lo) {
            return;
        }
        int lt = lo, gt = hi;
        int v = Charof(array[lo], d);
        int i = lo + 1;
        while (i <= gt) {
            int t = Charof(array[i], d);
            if (t < v) {
                exch(array, lt++, i++);
            } else if (t > v) {
                exch(array, i, gt--);
            } else {
                i++;
            }
        }
        Sort(array, lo, lt - 1, d);
        if (v >= 0) {
            Sort(array, lt, gt, d + 1);
        }
        Sort(array, gt + 1, hi, d);
    }

    public static int Charof(char[] array, int index) {//our charAt
        if (index < ArrayLen(array)) {
            return array[index];
        } else {
            return -1;
        }
    }

    public static void exch(char[][] a, int i, int j) {//exchange
        char[] temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static int ArrayLen(char array[]) {//our Length func
        int len = 0;
        while (true) {
            try {
                char c = array[len];
                len++;
            } catch (Exception e) {
                break;
            }
        }
        return len;
    }
    public static int ArrayLen(char array[][]) {
        int len = 0;
        while (true) {
            try {
                char []c = array[len];
                len++;
            } catch (ArrayIndexOutOfBoundsException  e) {
                break;
            }
        }
        return len;
    }
    
    public static int FileLen(File[]f){
        int len=0;
        while (true){
            try {
                File f2=f[len];
                len++;
            } catch (Exception e) {
                break;
            }
        }
        return len;
    }

    public static char[][] findDuplicates(char[][] array) {
        int[] j = {-1};
        int counter = 0;
        for (int i = 0; i < ArrayLen(array) - 1; i++) {
            if (array[i] != null && ArrayLen(array[i]) == ArrayLen(array[i + 1])
                    && BoyerMoore(array[i], array[i + 1], j, 0)) {
                array[i + 1] = null;
                counter++;
            }
        }
        char[][] array2 = new char[ArrayLen(array)- counter][];
        counter = 0;
        for (int i = 0; i < ArrayLen(array); i++) {
            if (array[i] != null) {
                array2[counter] = array[i];
                counter++;
            }
        }
        return array2;
    }
}
