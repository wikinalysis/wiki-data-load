defmodule WikiViewWeb.WikiController do
  use WikiViewWeb, :controller

  def index(conn, params) do
    wiki_pages =
      case params do
        %{"offset" => offset, "limit" => limit} ->
          WikiView.Wiki.list_page(%{
            limit: String.to_integer(limit),
            offset: String.to_integer(offset)
          })
        %{"limit" => limit} ->
          WikiView.Wiki.list_page(%{
            limit: String.to_integer(limit)
          })
        %{"offset" => offset} ->
          WikiView.Wiki.list_page(%{
            offset: String.to_integer(offset)
          })
        _ ->
          WikiView.Wiki.list_page()
      end

    render(conn, "index.html", pages: wiki_pages)
  end

  def get(conn, %{"id" => id}) do
    wiki_page = WikiView.Wiki.get_page!(id)
    render(conn, "show.html", page: wiki_page)
  end
end
