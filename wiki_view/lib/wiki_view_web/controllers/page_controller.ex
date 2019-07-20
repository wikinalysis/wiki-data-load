defmodule WikiViewWeb.PageController do
  use WikiViewWeb, :controller

  def index(conn, _params) do
    render(conn, "index.html")
  end
end
