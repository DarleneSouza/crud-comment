package controller;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import model.ModelException;
import model.Post;
import model.User;
import model.dao.DAOFactory;
import model.dao.PostDAO;
import model.dao.UserDAO;

public class CommonsController {
	
	public static void listUsers(HttpServletRequest req) {
		UserDAO dao = DAOFactory.createDAO(UserDAO.class);
		
		List<User> listUsers = null;
		try {
			listUsers = dao.listAll();
		} catch (ModelException e) {
			// Log no servidor
			e.printStackTrace();
		}
		
		if (listUsers != null)
			req.setAttribute("users", listUsers);		
	}
	public static void listPosts(HttpServletRequest req) {
		PostDAO dao = DAOFactory.createDAO(PostDAO.class);
		
		List<Post> listPosts = null;
		try {
			listPosts = dao.listAll();
		} catch (ModelException e) {
			// Log no servidor
			e.printStackTrace();
		}
		
		if (listPosts != null)
			req.setAttribute("posts", listPosts);		
	}
}
