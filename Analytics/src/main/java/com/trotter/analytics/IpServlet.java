package com.trotter.analytics;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.net.InetAddress;
import java.net.Inet6Address;

public class IpServlet extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    String ra = req.getRemoteAddr(); // creates a String
    InetAddress inetAddress = InetAddress.getByName(ra);

    String wes;
    if (inetAddress instanceof Inet6Address) {
      wes = "nope";
    } else {
      wes = "yep";
    }

    resp.setContentType("text/plain"); // sets the content type
    resp.setCharacterEncoding("UTF-8"); // sets the encoding
    resp.getWriter().write(ra + "  " + wes); // writes the value of the String to the response
  }
}