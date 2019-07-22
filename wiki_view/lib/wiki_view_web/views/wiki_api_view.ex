defmodule WikiViewWeb.WikiApiView do
  use WikiViewWeb, :view

  def render("index.json", %{pages: pages}) do
    %{data: Enum.map(pages, &page_json/1)}
  end

  def render("show.json", %{page: page} = params) do
    %{data: page_json(page)}
  end

  def page_json(page) do
    page
    |> trim_meta
    |> Map.merge(%{revisions: assoc_case(page.revision, &revision_json/1)})
    |> Map.drop([:text, :revision])
    |> Map.from_struct()
  end

  def revision_json(revision) do
    revision
    |> trim_meta
    |> Map.merge(%{text: assoc_case(revision.text, &text_json/1)})
    |> Map.delete(:page)
    |> Map.from_struct()
  end

  def text_json(text) do
    text
    |> trim_meta
    |> Map.from_struct()
  end

  defp assoc_case(arg, fun) do
    case arg do
      %Ecto.Association.NotLoaded{} ->
        nil

      [%{}] = args ->
        Enum.map(args, fun)

      %{} = my_arg ->
        fun.(my_arg)
    end
  end

  defp trim_meta(struct) do
    struct
    |> Map.delete(:__meta__)
  end
end
