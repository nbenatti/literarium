package literarium.parsingData.enumType;

public enum SearchType {
    SEARCH_BY_BOTH,
    SEARCH_BY_AUTHOR,
    SEARCH_BY_TITLE;

    public String searchTypeString() {
        switch (this) {
            case SEARCH_BY_AUTHOR:
                return "author";
            case SEARCH_BY_TITLE:
                return "title";
            default:
                return "all";
        }
    }
}
