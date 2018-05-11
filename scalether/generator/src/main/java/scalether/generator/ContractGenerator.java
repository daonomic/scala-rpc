package scalether.generator;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import scalether.generator.domain.TruffleContract;
import scalether.generator.domain.Type;
import scalether.generator.templates.ResourceTemplateLoader;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ContractGenerator {
    public final ObjectMapper mapper = createMapper();
    private final Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);

    public ContractGenerator() {
        configuration.setTemplateLoader(new ResourceTemplateLoader("templates"));
        configuration.setDefaultEncoding(StandardCharsets.UTF_8.displayName());
    }

    public static ObjectMapper createMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper;
    }

    public String generate(TruffleContract contract, String packageName, Type type) throws IOException, TemplateException {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            generate(out, contract, packageName, type);
            out.flush();
            return out.toString();
        }
    }

    public void generate(OutputStream out, TruffleContract contract, String packageName, Type type) throws IOException, TemplateException {
        try (Writer writer = new OutputStreamWriter(out)) {
            HashMap<String, Object> model = new HashMap<>();
            model.put("F", type.getF());
            model.put("monadType", type.getMonadType());
            model.put("monadImport", type.getMonadImport());
            model.put("transactionSender", type.getTransactionSender());
            model.put("transactionPoller", type.getTransactionPoller());
            model.put("imports", Arrays.asList(type.getImports()));
            model.put("preparedTransaction", type.getPreparedTransaction());
            model.put("truffle", contract);
            model.put("package", packageName);
            model.put("abi", escape(mapper.writeValueAsString(contract.getAbi())));
            generate(model, writer);
            writer.flush();
        }
    }

    private void generate(Map<String, Object> model, Writer writer) throws IOException, TemplateException {
        configuration.getTemplate("contract").process(model, writer);
    }

    private String escape(String raw) {
        return "\"" + raw.replace("\"", "\\\"") + "\"";
    }

    public static void main(String[] args) throws IOException, TemplateException {
        generate(args[0], args[1], args[2], (args.length > 3) ? Type.valueOf(args[3]) : Type.SCALA);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void generate(String jsonPath, String sourcePath, String packageName, Type type) throws IOException, TemplateException {
        ContractGenerator generator = new ContractGenerator();
        TruffleContract truffle;
        try (InputStream in = new FileInputStream(jsonPath)) {
            truffle = generator.mapper.readValue(in, TruffleContract.class);
        }
        String resultPath = sourcePath + "/" + packageName.replace(".", "/");
        new File(resultPath).mkdirs();
        try (OutputStream out = new FileOutputStream(new File(resultPath + "/" + truffle.getName() + ".scala"))) {
            generator.generate(out, truffle, packageName, type);
        }
    }
}
