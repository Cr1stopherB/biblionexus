import { createClient } from "@supabase/supabase-js";

// Variables de entorno configuradas en AWS Lambda > Configuration > Environment variables
const supabaseUrl = process.env.SUPABASE_URL?.replace(/\/+$/, "");
const supabaseKey = process.env.SUPABASE_ANON_KEY;

const supabase = createClient(supabaseUrl, supabaseKey);

const headers = {
  "Access-Control-Allow-Origin": "*",
  "Access-Control-Allow-Headers": "Content-Type,Authorization,apikey",
  "Access-Control-Allow-Methods": "GET,OPTIONS",
  "Content-Type": "application/json",
};

export const handler = async (event) => {
  if (event.httpMethod === "OPTIONS") {
    return { statusCode: 204, headers, body: "" };
  }

  const table = event.queryStringParameters?.table || "libros";
  const allowedTables = ["libros", "informes"];

  if (!allowedTables.includes(table)) {
    return {
      statusCode: 400,
      headers,
      body: JSON.stringify({ ok: false, error: "Tabla no permitida" }),
    };
  }

  const { data, error } = await supabase
    .from(table)
    .select("*")
    .order("created_at", { ascending: false });

  if (error) {
    return {
      statusCode: 502,
      headers,
      body: JSON.stringify({ ok: false, error: error.message }),
    };
  }

  return {
    statusCode: 200,
    headers,
    body: JSON.stringify({ ok: true, table, data }),
  };
};
