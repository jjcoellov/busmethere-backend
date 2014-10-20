package messages


class Comment {

    String author
    String text

    static belongsTo = [message:Message]

}
