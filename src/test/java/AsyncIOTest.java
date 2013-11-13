import org.junit.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**
 *
 */
public class AsyncIOTest {

    @Test
    public void computeCreditCardAsync() throws IOException {
        Path cards = FileSystems.getDefault().getPath("credit-card-numbers.csv");
        AsynchronousFileChannel ch = AsynchronousFileChannel.open(cards, StandardOpenOption.READ);


        ByteBuffer buf = ByteBuffer.allocateDirect(4096);

        Charset cs = Charset.forName("UTF-8");

        CompletionHandler<Integer, Object> handler = new CompletionHandler<Integer, Object>() {
            @Override
            public void completed(Integer result, Object attachment) {

            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        };

        int rd;
        long position = 0;
        while ( buf.hasRemaining() ) {
            ch.read(buf, position, null, handler);
            buf.rewind();
            System.out.println("String read: ");
            CharBuffer chbuf = cs.decode(buf);
            for ( int i = 0; i < chbuf.length(); i++ ) {
                /* print each character */
                System.out.print(chbuf.get());
            }
            buf.clear();
            position += 4096;
        }
    }
}
