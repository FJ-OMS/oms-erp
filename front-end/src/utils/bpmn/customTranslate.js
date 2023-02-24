/*
 * @Author: your name
 * @Date: 2021-10-12 17:42:31
 * @LastEditTime: 2021-10-12 17:42:46
 * @LastEditors: your name
 * @Description: In User Settings Edit
 * @FilePath: \newBj-admin\src\utils\bpmn\customTranslate.js
 */
import translations from './translationsGerman';

export default function customTranslate(template, replacements) {
  replacements = replacements || {};

  // Translate
  template = translations[template] || template;

  // Replace
  return template.replace(/{([^}]+)}/g, function(_, key) {
    var str = replacements[key];
    if (translations[replacements[key]] != null && translations[replacements[key]] != 'undefined') {
      str = translations[replacements[key]];
    }
    return str || '{' + key + '}';
  });
}
