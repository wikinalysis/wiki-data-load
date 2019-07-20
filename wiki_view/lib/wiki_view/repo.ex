defmodule WikiView.Repo do
  use Ecto.Repo,
    otp_app: :wiki_view,
    adapter: Ecto.Adapters.MyXQL
end
