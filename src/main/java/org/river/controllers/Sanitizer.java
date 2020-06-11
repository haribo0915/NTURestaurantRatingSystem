package org.river.controllers;

import org.river.entities.Comment;

import java.util.List;

/**
 * @author - Haribo
 */
public interface Sanitizer {
    List<Comment> queryCommentsWithFoulLanguage(List<Comment> comments);
}
