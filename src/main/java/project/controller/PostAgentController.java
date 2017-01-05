package project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import project.beanconfig.PathVar;
import project.entity.PostAgent;
import project.service.PostAgentService;

@Controller
public class PostAgentController {

    @Autowired
    private PostAgentService postAgentService;

    @RequestMapping(path = PathVar.POST_AGENT_FRAGMENT, method = RequestMethod.GET)
    @ResponseStatus(code = HttpStatus.OK)
    public String getPostAgents(Model model) {

	List<PostAgent> postlist = postAgentService.getAll();
	model.addAttribute("postagents", postlist);

	return "postagent";
    }

    @RequestMapping(path = PathVar.POST_AGENT_CREATE, method = RequestMethod.POST)
    @ResponseStatus(code = HttpStatus.OK)
    public String createPostAgent(@RequestParam(value = "postName") String postName,
	    @RequestParam(value = "postWebsite") String postWebsite) {

	System.out.println(postName + "," + postWebsite);
	postAgentService.createPostAgent(postName, postWebsite);
	return "index";
    }

    @RequestMapping(path = PathVar.POST_AGENT_EDIT, method = RequestMethod.POST)
    @ResponseStatus(code = HttpStatus.OK)
    public String editPostAgent(@RequestParam(value = "postId") Long id,
	    @RequestParam(value = "postName") String postName,
	    @RequestParam(value = "postWebsite") String postWebsite) {
	System.out.println(postName + "," + postWebsite + "," + id);

	postAgentService.editPostAgent(id, postName, postWebsite);
	return "index";
    }

    @RequestMapping(path = PathVar.POST_AGENT_REMOVE, method = RequestMethod.POST)
    @ResponseStatus(code = HttpStatus.OK)
    public String removePostAgent(@RequestParam(value = "postArray") long[] ids) {
	postAgentService.removePostAgent(ids);
	return "index";
    }

}
