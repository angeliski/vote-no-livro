package br.com.angeliski.mocks;

import java.util.ArrayList;

import javax.enterprise.inject.Vetoed;

import br.com.caelum.vraptor.View;
import br.com.caelum.vraptor.environment.Environment;
import br.com.caelum.vraptor.environment.NullEnvironment;
import br.com.caelum.vraptor.http.FormatResolver;
import br.com.caelum.vraptor.interceptor.DefaultTypeNameExtractor;
import br.com.caelum.vraptor.proxy.JavassistProxifier;
import br.com.caelum.vraptor.proxy.Proxifier;
import br.com.caelum.vraptor.serialization.DefaultRepresentationResult;
import br.com.caelum.vraptor.serialization.JSONSerialization;
import br.com.caelum.vraptor.serialization.RepresentationResult;
import br.com.caelum.vraptor.serialization.Serialization;
import br.com.caelum.vraptor.serialization.XMLSerialization;
import br.com.caelum.vraptor.serialization.gson.GsonBuilderWrapper;
import br.com.caelum.vraptor.serialization.gson.GsonJSONSerialization;
import br.com.caelum.vraptor.serialization.gson.GsonSerializerBuilder;
import br.com.caelum.vraptor.serialization.xstream.XStreamBuilder;
import br.com.caelum.vraptor.serialization.xstream.XStreamBuilderImpl;
import br.com.caelum.vraptor.serialization.xstream.XStreamXMLSerialization;
import br.com.caelum.vraptor.util.test.MockHttpServletResponse;
import br.com.caelum.vraptor.util.test.MockInstanceImpl;
import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.view.EmptyResult;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;

@Vetoed
public class MockSerializationResultCustom extends MockResult {

	private Serialization serialization;
	private MockHttpServletResponse response;
	private DefaultTypeNameExtractor extractor;
	private XStreamBuilder xstreambuilder;
	private GsonSerializerBuilder gsonBuilder;
	private Environment environment;

	/**
	 * @deprecated Prefer using
	 *             {@link MockSerializationResultCustom#MockSerializationResult(Proxifier, XStreamBuilder, GsonSerializerBuilder, Environment)}
	 *             that provides a {@link Environment}.
	 */
	public MockSerializationResultCustom(Proxifier proxifier, XStreamBuilder xstreambuilder, GsonSerializerBuilder gsonBuilder) {
		this(proxifier, xstreambuilder, gsonBuilder, new NullEnvironment());
	}

	public MockSerializationResultCustom(Proxifier proxifier, XStreamBuilder xstreambuilder, GsonSerializerBuilder gsonBuilder,
			Environment environment) {
		super(proxifier);
		this.environment = environment;
		this.response = new MockHttpServletResponse();
		this.extractor = new DefaultTypeNameExtractor();
		this.xstreambuilder = xstreambuilder;
		this.gsonBuilder = gsonBuilder;
	}

	public MockSerializationResultCustom() {
		this(new JavassistProxifier(), XStreamBuilderImpl.cleanInstance(), new GsonBuilderWrapper(new MockInstanceImpl<>(
				new ArrayList<JsonSerializer<?>>()), new MockInstanceImpl<>(new ArrayList<JsonDeserializer<?>>())));
	}

	public void resetResponse() {
		this.response = new MockHttpServletResponse();
	}

	@Override
	public <T extends View> T use(final Class<T> view) {
		this.typeToUse = view;
		if (view.equals(EmptyResult.class)) {
			return null;
		}
		return instanceView(view);
	}

	private <T extends View> T instanceView(Class<T> view) {
		if (view.isAssignableFrom(JSONSerialization.class)) {
			serialization = new GsonJSONSerialization(response, extractor, gsonBuilder, environment);
			return view.cast(serialization);
		}

		if (view.isAssignableFrom(XMLSerialization.class)) {
			serialization = new XStreamXMLSerialization(response, xstreambuilder, environment);
			return view.cast(serialization);
		}

		if (view.isAssignableFrom(RepresentationResult.class)) {
			serialization = new XStreamXMLSerialization(response, xstreambuilder, environment);
			return view.cast(new DefaultRepresentationResult(new FormatResolver() {
				@Override
				public String getAcceptFormat() {
					return "xml";
				}
			}, this, new MockInstanceImpl<>(this.serialization)));
		}

		return proxifier.proxify(view, returnOnFinalMethods(view));
	}

	/**
	 * Retrieve the string with the serialized (JSON/XML) Object if have one as
	 * response.
	 *
	 * @return String with the object serialized
	 */
	public String serializedResult() throws Exception {

		if ("application/xml".equals(response.getContentType())) {
			return response.getContentAsString();
		}

		if ("application/json".equals(response.getContentType())) {
			return response.getContentAsString();
		}

		return null;
	}

}
