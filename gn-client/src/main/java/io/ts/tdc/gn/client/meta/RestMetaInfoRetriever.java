package io.ts.tdc.gn.client.meta;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.ts.tdc.gn.common.MetaInfo;
import io.ts.tdc.gn.common.exception.ErrorCode;
import io.ts.tdc.gn.common.exception.GNException;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

/**
 * implementation via REST API request,
 * minimal usage of http client
 */
public class RestMetaInfoRetriever implements MetaInfoRetriever {
    public static final String metaInfoApiPath = "api/v1/metainfo";

    private CloseableHttpClient httpClient = HttpClients.createDefault();
    private String metaInfoSource;
    private ObjectMapper objectMapper = new ObjectMapper();

    public RestMetaInfoRetriever(String metaInfoSource) {
        this.metaInfoSource = metaInfoSource.endsWith("/") ? metaInfoSource : metaInfoSource + "/";
    }

    @Override
    public String metaInfoSource() {
        return metaInfoSource;
    }

    @Override
    public MetaInfo metaInfo() {
        HttpGet get = new HttpGet(metaInfoSource + metaInfoApiPath);
        try (CloseableHttpResponse httpResponse = httpClient.execute(get)) {
            int httpCode = httpResponse.getStatusLine().getStatusCode();
            if (httpCode < 200 || httpCode >= 300) {
                throw new GNException(ErrorCode.META_INFO_ERROR, "METAINFO API returns unsuccessful http code: " + httpCode);
            }
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity == null || httpEntity.getContent() == null) {
                throw new GNException(ErrorCode.META_INFO_ERROR, "METAINFO API returns empty entity body");
            }
            return objectMapper.readValue(httpEntity.getContent(), MetaInfo.class);
        } catch (IOException ioe) {
            throw new GNException(ErrorCode.META_INFO_ERROR, ioe);
        }
    }

    @Override
    public void close() throws Exception {
        httpClient.close();
    }
}
