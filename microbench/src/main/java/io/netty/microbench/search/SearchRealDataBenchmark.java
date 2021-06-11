package io.netty.microbench.search;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.search.AbstractMultiSearchProcessorFactory;
import io.netty.buffer.search.AbstractSearchProcessorFactory;
import io.netty.buffer.search.SearchProcessor;
import io.netty.buffer.search.SearchProcessorFactory;
import io.netty.microbench.util.AbstractMicrobenchmark;
import io.netty.util.internal.ResourcesUtil;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.annotations.CompilerControl.Mode;
import org.openjdk.jmh.infra.Blackhole;

import java.io.*;
import java.util.concurrent.TimeUnit;

@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 5)
@Measurement(iterations = 5)
@Fork(1)
public class SearchRealDataBenchmark extends AbstractMicrobenchmark {

    private static final byte[][] NEEDLES = {"Thank You".getBytes(), "* Does not exist *".getBytes(), "<li>".getBytes(), "<body>".getBytes(), "</li>".getBytes(), "github.com".getBytes(), " Does not exist 2 ".getBytes(), "</html>".getBytes(), "\"https://".getBytes(), "Netty 4.1.45.Final released".getBytes()};
    @Param
    public Algorithm algorithm;

    @Param
    public ByteBufType bufferType;

    private ByteBuf haystack;
    private SearchProcessorFactory[] searchProcessorFactories;
    private SearchProcessorFactory searchProcessorFactory;
    private int needleId, searchFrom, haystackLength;

    private static byte[] readBytes(File file) throws IOException {
        InputStream in = new FileInputStream(file);
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            try {
                byte[] buf = new byte[8192];
                for (; ; ) {
                    int ret = in.read(buf);
                    if (ret < 0) {
                        break;
                    }
                    out.write(buf, 0, ret);
                }
                return out.toByteArray();
            } finally {
                safeClose(out);
            }
        } finally {
            safeClose(in);
        }
    }

    private static void safeClose(InputStream in) {
        try {
            in.close();
        } catch (IOException ignored) {
        }
    }

    private static void safeClose(OutputStream out) {
        try {
            out.close();
        } catch (IOException ignored) {
        }
    }

    @Setup
    public void setup() throws IOException {
        File haystackFile = ResourcesUtil.getFile(SearchRealDataBenchmark.class, "netty-io-news.html");
        byte[] haystackBytes = readBytes(haystackFile);
        haystack = bufferType.newBuffer(haystackBytes);

        needleId = 0;
        searchFrom = 0;
        haystackLength = haystack.readableBytes();

        searchProcessorFactories = new SearchProcessorFactory[NEEDLES.length];
        for (int i = 0; i < NEEDLES.length; i++) {
            searchProcessorFactories[i] = algorithm.newFactory(NEEDLES[i]);
        }
    }

    @Setup(Level.Invocation)
    public void invocationSetup() {
        needleId = (needleId + 1) % searchProcessorFactories.length;
        searchProcessorFactory = searchProcessorFactories[needleId];
    }

    @TearDown
    public void teardown() {
        haystack.release();
    }

    @Benchmark
    @CompilerControl(Mode.DONT_INLINE)
    public int findFirst() {
        return haystack.forEachByte(searchProcessorFactory.newSearchProcessor());
    }

    @Benchmark
    @CompilerControl(Mode.DONT_INLINE)
    public int findFirstFromIndex() {
        searchFrom = (searchFrom + 100) % haystackLength;
        return haystack.forEachByte(searchFrom, haystackLength - searchFrom, searchProcessorFactory.newSearchProcessor());
    }

    @Benchmark
    @CompilerControl(Mode.DONT_INLINE)
    public void findAll(Blackhole blackHole) {
        SearchProcessor searchProcessor = searchProcessorFactory.newSearchProcessor();
        int pos = 0;
        do {
            pos = haystack.forEachByte(pos, haystackLength - pos, searchProcessor) + 1;
            blackHole.consume(pos);
        } while (pos > 0);
    }

    public enum Algorithm {
        AHO_CORASIC {
            @Override
            SearchProcessorFactory newFactory(byte[] needle) {
                return AbstractMultiSearchProcessorFactory.newAhoCorasicSearchProcessorFactory(needle);
            }
        }, KMP {
            @Override
            SearchProcessorFactory newFactory(byte[] needle) {
                return AbstractSearchProcessorFactory.newKmpSearchProcessorFactory(needle);
            }
        }, BITAP {
            @Override
            SearchProcessorFactory newFactory(byte[] needle) {
                return AbstractSearchProcessorFactory.newBitapSearchProcessorFactory(needle);
            }
        };

        abstract SearchProcessorFactory newFactory(byte[] needle);
    }

}
