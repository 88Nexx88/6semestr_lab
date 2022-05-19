import java.io.IOException;

public class Program {
    public static int file_size_bit;
    public static void main(String[] args) throws IOException {
        Tools tools = new Tools();
        //tools.generatedRandomByte();

        System.out.println("File: ");
        byte[] txt = tools.readFromFile(args[0]);
        //byte[] txt = tools.readFromFile("C:\\Users\\Danila\\IdeaProjects\\6semestr_lab3_crypto\\Отчёт_enc.docx");
        int str = 0;
        for(int i = 0; i < txt.length; i++){
            str += Integer.toBinaryString(txt[i]).length();
        }
        file_size_bit = str;
        FileParametrs fileParametrs = new FileParametrs(file_size_bit);
        System.out.println("Bit_test: ");
        float[] bit_chast =  fileParametrs.bit_test(txt);
        System.out.println("Byte test: ");
        float[] byte_chast = fileParametrs.byte_test(txt);
        System.out.println();
        System.out.println("Entrop_bit: ");
        float entr_bit = fileParametrs.entrop_bit(txt, bit_chast);
        System.out.println("Entrop_byte: ");
        float entr_byte = fileParametrs.entrop_byte(txt, byte_chast);
        System.out.println("Serial test: \n" +
                "i-length serial = n(i)");
        fileParametrs.serial_test(txt);
        System.out.println("Compress test: ");
        fileParametrs.compress(txt);

        System.out.println("Matrix rang test: ");
        fileParametrs.matrix_rang_test(txt);








        /*
        //test det matrix
        GFG gfg = new GFG();
        MatrixCalculation mt = new MatrixCalculation();


        double[][] test = new double[32][32];
        double[][] test1 = new double[32][32];
        for(int i = 0; i < test1.length; i++){
            for(int j = 0; j < test1.length; j++){
                test1[i][j] = 1;
            }
        }

        double[][] test2 = {
                {1, 0, 1, 0},
                {1, 0, 1, 0},
                {1, 0, 1, 0},
                {1, 0, 1, 0}
        };
        double[][] test3 = {
                {1, 0, 1, 0},
                {1, 1, 1, 0},
                {1, 1, 1, 0},
                {1, 0, 1, 1}
        };

        double[][] test4 = {
                {1, 0, 1, 0},
                {1, 1, 1, 1},
                {1, 1, 1, 1},
                {1, 0, 1, 1}
        };
        double[][] test5 = {
                {1, 0, 1, 0},
                {1, 1, 0, 1},
                {1, 0, 1, 1},
                {0, 0, 1, 1}
        };
        System.out.println(gfg.determinantMatrix(test));
        System.out.println(gfg.determinantMatrix(test1));
        System.out.println(gfg.determinantMatrix(test2) + " "+ mt.CalculateMatrix(test2));
        System.out.println(gfg.determinantMatrix(test3) + " "+ mt.CalculateMatrix(test3));
        System.out.println(gfg.determinantMatrix(test4) + " "+ mt.CalculateMatrix(test4));
        System.out.println(gfg.determinantMatrix(test5) + " "+ mt.CalculateMatrix(test5));

         */
    }



}
