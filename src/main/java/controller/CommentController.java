package controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Comment;
import model.ModelException;
import model.Post;
import model.User;
import model.dao.CommentDao;
import model.dao.DAOFactory;
import model.dao.PostDAO;
@WebServlet(urlPatterns = {"/comment", "/comment/form", "/comment/delete", "/comment/insert", "/comment/update"})
public class CommentController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
String action = req.getRequestURI();
		
		switch (action) {
		case "/crud-manager/comment/form": {
			CommonsController.listUsers(req);
			CommonsController.listPosts(req);

			req.setAttribute("action", "insert");
			ControllerUtil.forward(req, resp, "/form-comment.jsp");
			break;
		}
		case "/crud-manager/comment/update": {
			CommonsController.listUsers(req);
			CommonsController.listPosts(req);
			Comment c = loadComment(req);
			req.setAttribute("comment", c);
			req.setAttribute("action", "update");
			ControllerUtil.forward(req, resp, "/form-comment.jsp");
			break;
		}
		default:
			listComments(req);
			
			ControllerUtil.transferSessionMessagesToRequest(req);
		
			ControllerUtil.forward(req, resp, "/comments.jsp");
		}
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
String action = req.getRequestURI();
		
		if (action == null || action.equals("") ) {
			ControllerUtil.forward(req, resp, "/index.jsp");
			return;
		}
		
		switch (action) {
		case "/crud-manager/comment/delete":
			deleteComment(req, resp);
			break;
		case "/crud-manager/comment/insert": {
			insertComment(req, resp);
			break;
		}
		case "/crud-manager/comment/update": {
			updateComment(req, resp);
			break;
		}
		default:
			System.out.println("URL inválida " + action);
		}
			
		ControllerUtil.redirect(resp, req.getContextPath() + "/comment");
	}
	
	private Comment loadComment(HttpServletRequest req) {
		String postIdParameter = req.getParameter("commentId");
		
		int commentId = Integer.parseInt(postIdParameter);
		
		CommentDao dao = DAOFactory.createDAO(CommentDao.class);
		
		try {
			Comment c = dao.findById(commentId);
			
			if (c == null)
				throw new ModelException("comentario não encontrado para alteração");
			
			return c;
		} catch (ModelException e) {
			// log no servidor
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
		
		return null;
	}
	private void updateComment(HttpServletRequest req, HttpServletResponse resp) {
		String commentContent = req.getParameter("content");
		Integer userId = Integer.parseInt(req.getParameter("user"));
		
		Comment comment = loadComment(req);
		comment.setContent(commentContent);
		comment.setUser(new User(userId));
		
		CommentDao dao = DAOFactory.createDAO(CommentDao.class);
		
		try {
			if (dao.update(comment)) {
				ControllerUtil.sucessMessage(req, "Post '" + comment.getContent() + "' atualizado com sucesso.");
			}
			else {
				ControllerUtil.errorMessage(req, "Post '" + comment.getContent() + "' não pode ser atualizado.");
			}				
		} catch (ModelException e) {
			// log no servidor
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}
	
	private void insertComment(HttpServletRequest req, HttpServletResponse resp) {
		
		
		Comment comment = reqToComment(req);
		
		
		CommentDao dao = DAOFactory.createDAO(CommentDao.class);
		
		try {
			if (dao.save(comment)) {
				ControllerUtil.sucessMessage(req, "Comment '" + comment.getContent() + "' salvo com sucesso.");
			}
			else {
				ControllerUtil.errorMessage(req, "Comment '" + comment.getContent() + "' não pode ser salvo.");
			}
				
		} catch (ModelException e) {
			// log no servidor
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}
	
	private void deleteComment(HttpServletRequest req, HttpServletResponse resp) {
		String postIdParameter = req.getParameter("id");
		
		int commentId = Integer.parseInt(postIdParameter);
		
		CommentDao dao = DAOFactory.createDAO(CommentDao.class);
		
		try {
			Comment c = dao.findById(commentId);
			
			if (c == null)
				throw new ModelException("Post não encontrado para deleção");
			
			if (dao.delete(c)) {
				ControllerUtil.sucessMessage(req, "Comment '" + c.getContent() + "' deletado com sucesso.");
			}
			else {
				ControllerUtil.errorMessage(req, "Comment '" + c.getContent() + "' não pode ser deletado.");
			}
		} catch (ModelException e) {
			// log no servidor
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}
	private void listComments(HttpServletRequest req) {
		CommentDao dao = DAOFactory.createDAO(CommentDao.class);
		
		List<Comment> comments = null;
		try {
			comments = dao.listAll();
		} catch (ModelException e) {
			// Log no servidor
			e.printStackTrace();
		}
		
		if (comments != null)
			req.setAttribute("comments", comments);
	}
	
	private Comment reqToComment(HttpServletRequest req) {
		String content = req.getParameter("content");
	    int idPost = Integer.parseInt(req.getParameter("post"));
	    int idUser = Integer.parseInt(req.getParameter("user"));
	    String reacao = req.getParameter("reacao");
	    return new Comment( content, new Post(idPost), new User(idUser), reacao);
	}
}
