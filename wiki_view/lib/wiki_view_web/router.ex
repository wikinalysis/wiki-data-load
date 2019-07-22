defmodule WikiViewWeb.Router do
  use WikiViewWeb, :router

  pipeline :browser do
    plug :accepts, ["html"]
    plug :fetch_session
    plug :fetch_flash
    plug :protect_from_forgery
    plug :put_secure_browser_headers
  end

  pipeline :api do
    plug :accepts, ["json"]
  end

  scope "/api", WikiViewWeb do
    pipe_through :api

    get "/:id", WikiApiController, :show
    get "/", WikiApiController, :index
  end

  scope "/", WikiViewWeb do
    pipe_through :browser

    get "/:id", WikiController, :show
    get "/", WikiController, :index
  end
end
