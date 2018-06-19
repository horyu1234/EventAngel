package com.horyu1234.husuabieventlotteryapply.filter;

import com.googlecode.htmlcompressor.compressor.HtmlCompressor;
import com.googlecode.htmlcompressor.compressor.YuiCssCompressor;
import com.googlecode.htmlcompressor.compressor.YuiJavaScriptCompressor;
import com.horyu1234.husuabieventlotteryapply.CharResponseWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by horyu on 2018-01-15
 */
public class MinifyFilter implements Filter {
    private HtmlCompressor htmlCompressor;

    @Override
    public void init(FilterConfig filterConfig) {
        htmlCompressor = new HtmlCompressor();

        htmlCompressor.setCssCompressor(new YuiCssCompressor());
        htmlCompressor.setJavaScriptCompressor(new YuiJavaScriptCompressor());

        htmlCompressor.setCompressCss(true);
        htmlCompressor.setCompressJavaScript(true);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        CharResponseWrapper responseWrapper = new CharResponseWrapper(
                (HttpServletResponse) response);

        chain.doFilter(request, responseWrapper);

        String servletResponse = responseWrapper.toString();
        if (!response.isCommitted()) {
            response.getWriter().write(htmlCompressor.compress(servletResponse));
        }
    }

    @Override
    public void destroy() {
        // nothing
    }
}
