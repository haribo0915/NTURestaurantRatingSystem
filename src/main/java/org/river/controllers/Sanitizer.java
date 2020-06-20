package org.river.controllers;

import org.river.entities.Comment;

import java.util.List;

/**
 * @author - Haribo
 */
public interface Sanitizer {
    List<Comment> filterCommentsWithFoulLanguage(List<Comment> commentList);
}
