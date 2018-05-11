package scalether.generator;

import freemarker.template.TemplateException;
import scalether.generator.domain.AbiItem;
import scalether.generator.domain.TruffleContract;
import scalether.generator.domain.Type;

import java.io.*;
import java.util.Arrays;

public class ContractByAbiGenerator {
    public static void main(String[] args) throws IOException, TemplateException {
        generate(args[0], args[1], args[2], args[3], args.length > 4 ? Type.valueOf(args[4]) : Type.SCALA);
    }

    private static void generate(String name, String jsonPath, String sourcePath, String packageName, Type type) throws IOException, TemplateException {
        ContractGenerator generator = new ContractGenerator();
        AbiItem[] abi;
        try (InputStream in = new FileInputStream(jsonPath)) {
            abi = generator.mapper.readValue(in, AbiItem[].class);
        }
        TruffleContract truffle = new TruffleContract(name, Arrays.asList(abi), "0x");
        String resultPath = sourcePath + "/" + packageName.replace(".", "/");
        new File(resultPath).mkdirs();
        try (OutputStream out = new FileOutputStream(new File(resultPath + "/" + truffle.getName() + ".scala"))) {
            generator.generate(out, truffle, packageName, type);
        }
    }
}
