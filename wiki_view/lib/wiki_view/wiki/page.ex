defmodule WikiView.Wiki.Page do
  use Ecto.Schema
  import Ecto.Changeset

  @primary_key false
  schema "page" do
    field :page_id, :integer, primary_key: true
    field :page_content_model, :string
    field :page_is_new, :string
    field :page_is_redirect, :string
    field :page_lang, :string
    field :page_latest, :string
    field :page_len, :string
    field :page_links_updated, :string
    field :page_namespace, :integer
    field :page_random, :string
    field :page_restrictions, :string
    field :page_title, :string
    field :page_touched, :string
  end

  @doc false
  # def changeset(page, attrs) do
  #   page
  #   |> cast(attrs, [:page_namespace, :page_title, :page_restrictions, :page_is_redirect, :page_is_new, :page_random, :page_touched, :page_links_updated, :page_latest, :page_len, :page_content_model, :page_lang])
  #   |> validate_required([:page_namespace, :page_title, :page_restrictions, :page_is_redirect, :page_is_new, :page_random, :page_touched, :page_links_updated, :page_latest, :page_len, :page_content_model, :page_lang])
  # end
end
