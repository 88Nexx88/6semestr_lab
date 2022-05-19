import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.zip.GZIPOutputStream;

import static java.util.Objects.isNull;

public class FileParametrs {
    int size_file_bit;
//1000
    public FileParametrs(int size_file_bit){
        this.size_file_bit = size_file_bit;
    }


    public void matrix_rang_test(byte[] txt){
        BitSet array = BitSet.valueOf(txt);
        GFG calculate = new GFG();
        MatrixCalculation calculation = new MatrixCalculation();
        List<Boolean> result = new ArrayList<>();
        List<Boolean> result1 = new ArrayList<>();
        double[][] matrix = new double[32][32];
        double[][] matrix1 = new double[32][32];
        int count = 0;
        while (txt.length*8 - count*1024 >= 1024){
            for(int i = 0; i < 32; i++){
                for(int j = 0; j < 32; j++){
                    matrix[i][j] = (array.get(count*1024 + i + j)) ? 1 : 0;
                    matrix1[i][j] = (array.get(count*1024 + i + j)) ? 1 : 0;
                }
            }

            boolean res = calculate.determinantMatrix(matrix);
            //boolean res1 = (calculation.CalculateMatrix(matrix1) == 0) ? true : false;
            result.add(count, res);
            //result1.add(count, res1);
            count++;

        }
        System.out.println(result.toString());
        int sumT = 0;
        for(boolean b : result){
            if(b) sumT++;
        }
        System.out.println("Count True = "+sumT + " % = "+ (double) sumT / result.size());
        //System.out.println(result1.toString());
    }

    public byte[] compress(byte[] txt) {
        try (final ByteArrayOutputStream baos = new ByteArrayOutputStream();
             final GZIPOutputStream gzipOutput = new GZIPOutputStream(baos)) {
            gzipOutput.write(txt);
            gzipOutput.finish();
            System.out.println((float)baos.toByteArray().length / txt.length + "\nn-compress: "+baos.toByteArray().length +" n-source: "+txt.length);
            return baos.toByteArray();
        } catch (IOException e) {
            throw new UncheckedIOException("Error while compression!", e);
        }
    }


    public void serial_test(byte[] txt){
        HashMap<Integer, Integer> result = new HashMap<>();
        BitSet array = BitSet.valueOf(txt);
        int count = 0;
        for (int i = 0; i < array.length(); i++){
            if(array.get(i) == true) {
                count++;
            }
            else {
                if(result.get(count) == null){
                    result.put(count, 1);
                }
                result.put(count, result.get(count) + 1);
                count = 0;
            }
        }
        System.out.println(result.toString());
    }


    public float entrop_byte(byte[] txt, float[] byte_chast){
        float result = 0;
        for(int i = 0; i < byte_chast.length; i++){
            if(byte_chast[i] != 0)
                result += byte_chast[i] * (float)(Math.log(byte_chast[i]) / Math.log(2));
        }
        result *= -1;
        System.out.println("A-byte = "+result);
        return result;
    }


    public float entrop_bit(byte[] txt, float[] bit_chast){
        float result;
        System.out.println("A-bit = "+ (-bit_chast[0] * (float)Math.log(bit_chast[0]) / Math.log(2) -
                bit_chast[1] * (float)(Math.log(bit_chast[1]) / Math.log(2))));
        result = (-bit_chast[0] * (float)Math.log(bit_chast[0] / Math.log(2) -
                bit_chast[1] * (float)Math.log(bit_chast[1] / Math.log(2))));
        return result;
    }

    public float[] byte_test(byte[] txt){
        int[] count_byte = new int[256];
        for(byte b : txt){
            count_byte[b & 0xFF] = count_byte[b & 0xFF] + 1;
        }
        int c = 0;
        float[] result = new float[256];
        for(int i : count_byte){
            result[c] = (float) i / txt.length;
            System.out.print(c+" byte - "+result[c] + " ; ");
            c++;
        }
        return result;
    }

    public float[] bit_test(byte[] txt){
        int sum_count = 0;
        int sum_false = 0;
        BitSet arrayBit = BitSet.valueOf(txt);
        //System.out.println(txt.length + " "+ arrayBit.length()+" "+arrayBit.size());
        for(int i = 0; i < txt.length * 8; i++){
            if(arrayBit.get(i)){
                sum_count++;
            }
            else sum_false++;
            //System.out.print(arrayBit.get(i) ? 1 : 0);
        }
        //0000110 010000110 01000110
        System.out.println();
        System.out.println("n1 = "+sum_count + " n0= "+ sum_false);
        System.out.println("p1 = "+(float)sum_count/(txt.length*8) + " " + "p0 = "+ (1 - ((float)sum_count/(txt.length*8))));

        float[] result = {(float)sum_count/(txt.length*8), (1 - ((float)sum_count/(txt.length*8)))};
        return result;
    }
    private int bitCounter(int n) {
        int count = 0;
        while (n != 0) {
            count++;
            n = n & (n - 1);
        }
        return count;
    }

}
