defmodule WikiView.Wiki do
  @moduledoc """
  The Wiki context.
  """

  import Ecto.Query, warn: false
  alias WikiView.Repo

  alias WikiView.Wiki.Page

  @doc """
  Returns the list of page.

  ## Examples

      iex> list_page()
      [%Page{}, ...]

  """
  def list_page(opts \\ %{}) do
    default = %{limit: 50, offset: 0}
    options = Map.merge(default, opts)
    Repo.all(from p in Page, limit: ^options.limit, offset: ^options.offset)
  end

  @doc """
  Gets a single page.

  Raises `Ecto.NoResultsError` if the Page does not exist.

  ## Examples

      iex> get_page!(123)
      %Page{}

      iex> get_page!(456)
      ** (Ecto.NoResultsError)

  """
  def get_page!(id) do
    Page
    |> preload([:revision, :text])
    |> Repo.get!(id)
  end

  @doc """
  Returns an `%Ecto.Changeset{}` for tracking page changes.

  ## Examples

      iex> change_page(page)
      %Ecto.Changeset{source: %Page{}}

  """
  def change_page(%Page{} = page) do
    Page.changeset(page, %{})
  end

  alias WikiView.Wiki.Revision

  @doc """
  Returns the list of revisions.

  ## Examples

      iex> list_revisions()
      [%Revision{}, ...]

  """
  def list_revisions do
    Repo.all(Revision)
  end

  @doc """
  Gets a single revision.

  Raises `Ecto.NoResultsError` if the Revision does not exist.

  ## Examples

      iex> get_revision!(123)
      %Revision{}

      iex> get_revision!(456)
      ** (Ecto.NoResultsError)

  """
  def get_revision!(id), do: Repo.get!(Revision, id)

  @doc """
  Returns an `%Ecto.Changeset{}` for tracking revision changes.

  ## Examples

      iex> change_revision(revision)
      %Ecto.Changeset{source: %Revision{}}

  """
  def change_revision(%Revision{} = revision) do
    Revision.changeset(revision, %{})
  end

  alias WikiView.Wiki.Text

  @doc """
  Returns the list of texts.

  ## Examples

      iex> list_texts()
      [%Text{}, ...]

  """
  def list_texts do
    Repo.all(Text)
  end

  @doc """
  Gets a single text.

  Raises `Ecto.NoResultsError` if the Text does not exist.

  ## Examples

      iex> get_text!(123)
      %Text{}

      iex> get_text!(456)
      ** (Ecto.NoResultsError)

  """
  def get_text!(id), do: Repo.get!(Text, id)

  @doc """
  Returns an `%Ecto.Changeset{}` for tracking text changes.

  ## Examples

      iex> change_text(text)
      %Ecto.Changeset{source: %Text{}}

  """
  def change_text(%Text{} = text) do
    Text.changeset(text, %{})
  end
end
