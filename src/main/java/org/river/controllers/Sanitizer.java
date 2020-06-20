package org.river.controllers;

import org.river.entities.Comment;

import java.util.List;

/**
 * The sanitizer is used to filter the comments with self defined rules and algorithm.
 *
 * @author - Haribo
 */
public interface Sanitizer {
    List<Comment> sanitize(List<Comment> commentList);
}
