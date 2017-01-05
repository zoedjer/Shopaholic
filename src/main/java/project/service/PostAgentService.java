package project.service;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.dbaccess.PostAgentTable;
import project.entity.PostAgent;

@Service
@Transactional
public class PostAgentService {

	@Autowired
	private PostAgentTable postAgentTable;

	private List<PostAgent> allPosts;

	public void removePostAgent(long... ids) {
		postAgentTable.remove(ids);
	}

	public void createPostAgent(String name, String website) {
		postAgentTable.insert(name, website);
	}
	
	public void editPostAgent(Long id, String name, String website) {
		postAgentTable.edit(id, name, website);
	}

	public List<PostAgent> getAll() {
		allPosts = postAgentTable.getAllPosts();
		return allPosts;
	}

	public Set<String> getAllPostNames() {

		Set<String> names = new TreeSet<>();

		if (allPosts == null) {
			allPosts = postAgentTable.getAllPosts();
		}

		for (PostAgent pa : allPosts) {
			names.add(pa.getName());
		}

		return names;
	}

}
