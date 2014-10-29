package br.com.angeliski.view.home;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;

@Controller
@Path("/")
public class HomeController {

	@Get
	@Path("/")
	public void inicio() {
	}

}
