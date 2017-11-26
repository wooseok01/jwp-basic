package next.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wooseokSong on 2017-11-27.
 */
public interface Controller {
	String excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
