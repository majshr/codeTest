package codeTest.v1.nio;

import java.nio.charset.Charset;

public class CharSetTest {
    public static void main(String[] args) {
        Charset charset = Charset.forName("UTF-8");
        // 编码
        charset.encode("aaa");
    }

}
