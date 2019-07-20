defmodule WikiViewWeb.WikiController do
    use WikiViewWeb, :controller
  
    def index(conn, _params) do
      wiki_pages = WikiView.Wiki.list_page()
      render(conn, "index.html", pages: wiki_pages)
    end
  end
  