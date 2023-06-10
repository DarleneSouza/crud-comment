package model;

import java.util.Calendar;
import java.util.Date;

public class Comment {

    private int id;
    private String content;
    private Post post;
    private User user;
	private Date commentDate;
    private String reacao;

	public Comment( String content, Post post, User user, String reacao) {
        this.content = content;
        this.post = post;
        this.user = user;
        this.reacao = reacao;
        this.commentDate = Calendar.getInstance().getTime();
    }
	public Comment(int id, String content, Post post, User user, String reacao, Date commentDate) {
        this.id = id;
        this.content = content;
        this.post = post;
        this.user = user;
        this.reacao = reacao;
        this.commentDate = commentDate;
    }
    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Post getPost() {
		return post;
	}
	public void setPost(Post post) {
		this.post = post;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getReacao() {
		return reacao;
	}
	public void setReacao(String reacao) {
		this.reacao = reacao;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Date getCommentDate() {
		return commentDate;
	}
	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}
}