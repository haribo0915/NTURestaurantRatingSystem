package org.river.models;

import org.river.entities.Comment;

import java.util.List;

/**
 * @author - Haribo
 */
public interface FoulLanguageGuard {
    List<Comment> queryCommentsWithFoulLanguage(List<Comment> comments);
}
