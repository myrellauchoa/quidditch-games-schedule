package com.myrella.quidditch.games.schedule.api.service;

import com.myrella.quidditch.games.schedule.api.dao.GameDAO;
import com.myrella.quidditch.games.schedule.api.entity.Game;
import com.myrella.quidditch.games.schedule.api.entity.GameUpdatePayload;
import com.myrella.quidditch.games.schedule.api.exceptions.DateAlreadyRegisteredException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class GameService {

    @Autowired
    private GameDAO gameDAO;

    public Collection<Game> getGames() {
        return gameDAO.getGames();
    }

    public Game createGame(Game game) {
        opponentNotEmpty(game);
        isGameDateExists(game);
        return gameDAO.createGame(game);

    }

    private boolean opponentNotEmpty(Game game) {
        if (game.getOpponent1().isEmpty() || game.getOpponent2().isEmpty()) {
            throw new IllegalArgumentException("Opponent cannot be empty!");
        } else {
            return true;
        }
    }

    private boolean isGameDateExists(Game game) {
        var gameDateExists = getGames().stream().anyMatch(date -> date.getDate().equals(game.getDate()));

        if (gameDateExists) {
            throw new DateAlreadyRegisteredException(game.getDate());
        } else {
            return true;
        }
    }



    public Optional<Game> getGameById(int id) {
        return gameDAO.getGameById(id);
    }


    public Optional<Game> deleteGameById(int id) {
        return gameDAO.deleteGameById(id);
    }

    public Optional<Game> updateGameById(int id, GameUpdatePayload gameUpdatePayload) {
        return gameDAO.updateGameById(id, gameUpdatePayload);
    }


}
