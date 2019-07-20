defmodule WikiView.Wiki.Page do
  use Ecto.Schema
  import Ecto.Changeset

  @primary_key false
  schema "page" do
    field :id, :integer, primary_key: true, source: :page_id
    field :content_model, :string, source: :page_content_model
    field :is_new, :string, source: :page_is_new
    field :is_redirect, :string, source: :page_is_redirect
    field :language, :string, source: :page_lang
    field :latest, :string, source: :page_latest
    field :length, :string, source: :page_len
    field :links_updated, :string, source: :page_links_updated
    field :namespace, :integer, source: :page_namespace
    field :random, :string, source: :page_random
    field :restrictions, :string, source: :page_restrictions
    field :title, :string, source: :page_title
    field :touched, :string, source: :page_touched
  end

  @doc false
  def changeset(page, attrs) do
    page
    |> cast(attrs, [:namespace, :title, :restrictions, :is_redirect, :is_new, :random, :touched, :links_updated, :latest, :len, :content_model, :language])
    |> validate_required([:namespace, :title, :restrictions, :is_redirect, :is_new, :random, :touched, :links_updated, :latest, :length, :content_model, :language])
  end
end
