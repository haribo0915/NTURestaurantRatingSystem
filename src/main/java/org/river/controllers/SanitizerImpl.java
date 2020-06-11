package org.river.controllers;

import org.river.entities.Comment;

import java.util.List;

/**
 * @author - Haribo
 */
public class SanitizerImpl implements Sanitizer {
    @Override
    public List<Comment> queryCommentsWithFoulLanguage(List<Comment> comments) {
        return null;
    }
}
