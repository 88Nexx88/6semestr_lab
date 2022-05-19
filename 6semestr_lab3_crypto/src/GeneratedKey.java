import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


class GeneratedKey{

    public List<Integer> sdvig(int N){
        List<Integer> key_sdvig = new ArrayList<>();
        for(int i = 0; i < N; i++){
            key_sdvig.add(StdRandom.uniform(0, 8));
        }
        for(int i : key_sdvig){
            System.out.print(i+ " ");
        }
        return key_sdvig;
    }

    public byte[] substitution(){
        List<Byte> key = new ArrayList<>();
        byte[] key1 = new byte[256];
        for(int i = 0; i < 256; i++){
            key.add((byte)i);
        }
        Collections.shuffle(key);
        int c = 0;
        for(byte b : key){
            System.out.print(b+" ");
            key1[c] = b;
            c++;
        }
        System.out.println();
        return key1;
    }

    public List<Integer> permutation(int N){
        List<Integer> key = new ArrayList<>();
        for(int i = 0; i < N; i++){
            key.add(i);
        }
        Collections.shuffle(key);
        return key;
    }

    public byte[] gammirov(int N){
        byte[] key = new byte[N];
        for(int i = 0; i < key.length; i++){
            key[i] = (byte)StdRandom.uniform(0, 256);
        }
        for(byte b : key){
            System.out.print(b+" ");
        }
        System.out.println();
        return key;
    }
}
