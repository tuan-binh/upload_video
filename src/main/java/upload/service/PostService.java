package upload.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upload.model.Post;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
	
	@Autowired
	private DataSource dataSource;
	private Connection con = null;
	
	public List<Post> findAll() {
		List<Post> list = new ArrayList<>();
		try {
			con = dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		try {
			CallableStatement callSt = con.prepareCall("{call FIND_ALL}");
			ResultSet rs = callSt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String url = rs.getString("url");
				Post post = new Post(id, name, url);
				list.add(post);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
		}
		return list;
	}
	
	public void save(Post post) {
		try {
			con = dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		if (post.getId() == 0) {
			try {
				CallableStatement callSt = con.prepareCall("{call INSERT_POST(?,?)}");
				callSt.setString(1, post.getName());
				callSt.setString(2, post.getUrl());
				callSt.executeUpdate();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			} finally {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						throw new RuntimeException(e);
					}
				}
			}
		}
	}
	
	public void delete(int id) {
		try {
			con = dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		try {
			CallableStatement callSt = con.prepareCall("{call DELETE_BY_ID(?)}");
			callSt.setInt(1, id);
			callSt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}
	
	public Post findById(int id_find) {
		Post post = null;
		try {
			con = dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		try {
			CallableStatement callSt = con.prepareCall("{call FIND_BY_ID(?)}");
			callSt.setInt(1,id_find);
			ResultSet rs = callSt.executeQuery();
			while (rs.next()) {
				post = new Post();
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String url = rs.getString("url");
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
		}
		return post;
	}
	
}
