defmodule WikiView.Wiki.Page do
  use Ecto.Schema
  import Ecto.Changeset

  @primary_key false
  schema "page" do
    field :id, :integer, primary_key: true, source: :page_id
    field :namespace, :integer, source: :page_namespace
    field :title, :string, source: :page_title
    field :restrictions, :string, source: :page_restrictions
    field :is_redirect, :string, source: :page_is_redirect
    field :is_new, :string, source: :page_is_new
    field :random, :string, source: :page_random
    field :touched, :string, source: :page_touched
    field :links_updated, :string, source: :page_links_updated
    field :latest, :string, source: :page_latest
    field :length, :string, source: :page_len
    field :content_model, :string, source: :page_content_model
    field :language, :string, source: :page_lang
  end

  @doc false
  def changeset(page, attrs) do
    page
    |> cast(attrs, [:namespace, :title, :restrictions, :is_redirect, :is_new, :random, :touched, :links_updated, :latest, :len, :content_model, :language])
    |> validate_required([:namespace, :title, :restrictions, :is_redirect, :is_new, :random, :touched, :links_updated, :latest, :length, :content_model, :language])
  end
end
