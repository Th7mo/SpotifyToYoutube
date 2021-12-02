package nl.th7mo.spotify;

import nl.th7mo.connection.BadRequestException;

import java.io.IOException;

public interface TokenDAO {

    Token getToken() throws IOException, BadRequestException;
}
