select 
fa.id_familia,
fa.nome_familia,
pai.id_animal as pai_id,
pai.nome_animal as  pai_nome,
pai.tag_animal  as pai_tag,
pai.sisbov_animal as pai_sisbov,
mae.id_animal as mae_id,
mae.nome_animal as mae_nome,
mae.tag_animal  as mae_tag,
mae.sisbov_animal as mae_sisbov
from familia fa 
left join animais pai on fa.id_animal_pai = pai.id_animal
left join animais mae on fa.id_animal_mae = mae.id_animal