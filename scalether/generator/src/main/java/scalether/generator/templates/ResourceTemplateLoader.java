package scalether.generator.templates;

import freemarker.cache.TemplateLoader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class ResourceTemplateLoader implements TemplateLoader {
    private final String basePath;

    public ResourceTemplateLoader(String basePath) {
        this.basePath = basePath;
    }

    @Override
    public Object findTemplateSource(String name) {
        return getClass().getClassLoader().getResourceAsStream(basePath + "/" + name + ".ftl");
    }

    @Override
    public long getLastModified(Object templateSource) {
        return 0;
    }

    @Override
    public Reader getReader(Object templateSource, String encoding) throws IOException {
        return new InputStreamReader((InputStream) templateSource, encoding);
    }

    @Override
    public void closeTemplateSource(Object templateSource) throws IOException {
        ((InputStream) templateSource).close();
    }
}
