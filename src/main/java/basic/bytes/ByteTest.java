package basic.bytes;

public class ByteTest {

    public static void main(String[] args) {

        // 二进制
        int binaryValue = 0b1;

        // 十进制
        int decimalValue = 1;

        // 八进制
        int octalValue = 01;

        // 16进制
        int hexValue = 0x1;


        short s = 32767;

        binaryValue();
    }

    private static void binaryValue() {
        int i10 = -0b1010;
        System.out.println(i10);
    }
}
