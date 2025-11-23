package org.e4s.benchmark;

import java.util.concurrent.TimeUnit;

import org.e4s.benchmark.service.Greeting;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
// For JUnit 4 @RunWith(SpringRunner.class), or use @ExtendWith(SpringExtension.class) for JUnit 5
@State(Scope.Benchmark) // Define the scope of the benchmark state
@BenchmarkMode(Mode.Throughput) // Specify the benchmark mode (e.g., AverageTime, Throughput)
@OutputTimeUnit(TimeUnit.MICROSECONDS) // Define the output time unit
public class BenchmarkApplicationTests {

    private static Greeting greeting; // Inject your Spring component


    /**
     * We use setter autowiring to make Spring save an instance of `DSLContext` into a
     * static field accessible be the benchmarks spawned through the JMH runner.
     *
     * @param greeting service from ctx
     */
    @Autowired
    void setDslContext(Greeting greeting) {
        BenchmarkApplicationTests.greeting = greeting;
    }

    @Setup(Level.Trial)
    public void setup() {
        // Optional: Perform setup before all benchmark iterations
    }

    @Benchmark
    public void benchmarkGreetingMethod() {
        greeting.sayHello("eric"); // Call the method to be benchmarked
    }

    @TearDown(Level.Trial)
    public void teardown() {
        // Optional: Perform cleanup after all benchmark iterations
    }

    @Test
    public void runner() throws RunnerException {
        Options opt = new OptionsBuilder()
            .include("\\." + BenchmarkApplicationTests.class.getSimpleName() +"\\.")
            .warmupIterations(2)
            .warmupTime(TimeValue.seconds(10))
            .measurementIterations(3)
//            .measurementTime(TimeValue.seconds(10))
            .forks(0) //0 makes debugging possible
            .shouldFailOnError(true)
//            .addProfiler(GCProfiler.class)
            .build();

        new Runner(opt).run();
    }

}
