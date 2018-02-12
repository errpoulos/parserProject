package src;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class TokenizerTest {





    @Test
    public void InstantiateTokenizer() {
        Tokenizer tokenizer = new Tokenizer();
        assertNotNull(tokenizer);
    }

    @Test
    public void TokenizerAddTest(){
        Tokenizer tokenizer = new Tokenizer();
//        assertNotNull(tokenizer.add("\\A", 1));

    }
}