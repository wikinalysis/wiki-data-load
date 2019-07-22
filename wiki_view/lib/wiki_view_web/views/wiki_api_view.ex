defmodule WikiViewWeb.WikiApiView do
  use WikiViewWeb, :view

  def render("index.json", %{pages: pages}) do
    %{data: Enum.map(pages, &page_json/1)}
  end

  def render("show.json", %{page: page} = _params) do
    %{data: page_json(page)}
  end

  def page_json(page) do
    page
    |> trim_meta
    |> Map.merge(%{
      revision: assoc_case(page.revision, &revision_json/1),
      is_new: convert_bool(page.is_new),
      is_redirect: convert_bool(page.is_redirect),
      touched: to_datetime(page.touched)
    })
    |> Map.drop([:text, :revisions])
    |> Map.from_struct()
  end

  def revision_json(revision) do
    revision
    |> trim_meta
    |> Map.merge(%{
      text: assoc_case(revision.text, &text_json/1),
      timestamp: to_datetime(revision.timestamp)
    })
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

  defp convert_bool(val) when is_integer(val) do
    if val == 0 do
      false
    else
      true
    end
  end

  # This whole thing is a little unneccessary, but I wanted to play with bitstring matching
  def to_datetime(<<date::bytes-size(8)>> <> <<time::bytes-size(6)>>) do
    {:ok, result} = NaiveDateTime.new(to_date(date), to_time(time))
    result
  end

  def to_date(<<year::bytes-size(4)>> <> <<mon::bytes-size(2)>> <> <<day::bytes-size(2)>>) do
    {:ok, result} =
      Date.new(String.to_integer(year), String.to_integer(mon), String.to_integer(day))

    result
  end

  def to_time(<<hour::bytes-size(2)>> <> <<min::bytes-size(2)>> <> <<sec::bytes-size(2)>>) do
    {:ok, result} =
      Time.new(String.to_integer(hour), String.to_integer(min), String.to_integer(sec))

    result
  end
end
