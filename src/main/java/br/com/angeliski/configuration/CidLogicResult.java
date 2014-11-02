package br.com.angeliski.configuration;

import static br.com.caelum.vraptor.proxy.CDIProxies.unproxifyIfPossible;
import static com.google.common.base.Preconditions.checkArgument;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Specializes;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.core.MethodInfo;
import br.com.caelum.vraptor.http.MutableRequest;
import br.com.caelum.vraptor.http.route.Router;
import br.com.caelum.vraptor.interceptor.TypeNameExtractor;
import br.com.caelum.vraptor.ioc.Container;
import br.com.caelum.vraptor.proxy.MethodInvocation;
import br.com.caelum.vraptor.proxy.Proxifier;
import br.com.caelum.vraptor.proxy.ProxyInvocationException;
import br.com.caelum.vraptor.proxy.SuperMethod;
import br.com.caelum.vraptor.view.DefaultLogicResult;
import br.com.caelum.vraptor.view.FlashScope;
import br.com.caelum.vraptor.view.PathResolver;

@Specializes
@RequestScoped
public class CidLogicResult extends DefaultLogicResult {

	private static final Logger logger = LoggerFactory
			.getLogger(DefaultLogicResult.class);
	private Proxifier proxifier;
	private Router router;
	private MutableRequest request;
	private HttpServletResponse response;
	private Result result;

	/**
	 * @deprecated CDI eyes only
	 */
	public CidLogicResult() {
	}

	@Inject
	public CidLogicResult(Proxifier proxifier, Router router,
			MutableRequest request, HttpServletResponse response,
			Container container, PathResolver resolver,
			TypeNameExtractor extractor, FlashScope flash,
			MethodInfo methodInfo, Result result) {
		super(proxifier, router, request, response, container, resolver,
				extractor, flash, methodInfo);
		this.proxifier = proxifier;
		this.response = unproxifyIfPossible(response);
		this.request = unproxifyIfPossible(request);
		this.router = router;

		this.result = result;
	}

	@Override
	public <T> T redirectTo(final Class<T> type) {
		logger.debug("redirecting to class {}", type.getSimpleName());

		return proxifier.proxify(type, new MethodInvocation<T>() {
			@Override
			public Object intercept(T proxy, Method method, Object[] args,
					SuperMethod superMethod) {
				checkArgument(acceptsHttpGet(method),
						"Your logic method must accept HTTP GET method if you want to redirect to it");

				try {
					String url = router.urlFor(type, method, args);
					String path = request.getContextPath() + url;
					path = includeCid(path);
					includeParametersInFlash(type, method, args);

					logger.debug("redirecting to {}", path);
					response.sendRedirect(path);
					return null;
				} catch (IOException e) {
					throw new ProxyInvocationException(e);
				}
			}
		});
	}

	private String includeCid(String path) {
		if (request.getParameter("cid") != null) {
			path = path + "?cid=" + request.getParameter("cid");
		} else if (result.included().get("cid") != null) {
			path = path + "?cid=" + result.included().get("cid");
		}
		return path;
	}
}
