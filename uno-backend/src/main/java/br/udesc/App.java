package br.udesc;

import com.google.common.base.CaseFormat;

import br.udesc.core.server.Server;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {

        Server server = Server.getInstance();
        server.startServer();
    }
}
